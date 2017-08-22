package org.sysu.lucene;

import java.io.StringReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.sysu.pojo.InfoBook;
import org.sysu.utils.StringUtil;

public class MyLucene {
	private Directory dir = null;
	
	/**
	 * 获取IndexWriter实例
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter() throws Exception {
		dir = FSDirectory.open(Paths.get("G:\\Lucene"));
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	
	/**
	 * 添加数据
	 * @param infoBook
	 */
	public void addIndex(InfoBook infoBook) throws Exception {
		IndexWriter writer = getWriter();
		Document doc = new Document();
		/**
		 * yes是会将数据存进索引，如果查询结果中需要将记录显示出来就要存进去，如果查询结果
		 * 只是显示标题之类的就可以不用存，而且内容过长不建议存进去
		 * 使用TextField类是可以用于查询的。
		 */
		doc.add(new StringField("id", String.valueOf(infoBook.getId()), Field.Store.YES));
		//doc.add(new TextField("isbn", infoBook.getIsbn(), Field.Store.YES));
		doc.add(new TextField("title", infoBook.getTitle(), Field.Store.YES));
		doc.add(new TextField("author", infoBook.getAuthor(), Field.Store.YES));
		doc.add(new TextField("publisher", infoBook.getPublisher(), Field.Store.YES));
		doc.add(new TextField("publishedin", (new SimpleDateFormat("yyyy")).format(infoBook.getPublishedin()), Field.Store.YES));
		doc.add(new TextField("description", infoBook.getDescription(), Field.Store.YES));
		writer.addDocument(doc);
		writer.close();		
	}
	
	/**
	 * 更新图书索引
	 * @param infoBook
	 * @throws Exception
	 */
	public void updateIndex(InfoBook infoBook) throws Exception {
		IndexWriter writer = getWriter();
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(infoBook.getId()), Field.Store.YES));
		//doc.add(new TextField("isbn", infoBook.getIsbn(), Field.Store.YES));
		doc.add(new TextField("title", infoBook.getTitle(), Field.Store.YES));
		doc.add(new TextField("author", infoBook.getAuthor(), Field.Store.YES));
		doc.add(new TextField("publisher", infoBook.getPublisher(), Field.Store.YES));
		doc.add(new TextField("publishedin", (new SimpleDateFormat("yyyy")).format(infoBook.getPublishedin()), Field.Store.YES));
		doc.add(new TextField("description", infoBook.getDescription(), Field.Store.YES));
		writer.updateDocument(new Term("id", String.valueOf(infoBook.getId())), doc);
		writer.close();
	}
	
	/**
	 * 删除指定图书的索引
	 * @param infoBookId
	 * @throws Exception
	 */
	public void deleteIndex(String infoBookId) throws Exception {
		IndexWriter writer = getWriter();
		writer.deleteDocuments(new Term("id", infoBookId));
		writer.forceMergeDeletes();//强制删除
		writer.commit();
		writer.close();
	}
	
	/**
	 * 查询用户
	 * @param q 查询关键字
	 * @return
	 * @throws Exception
	 */
	public List<InfoBook> searchBook(String q) throws Exception {
		dir = FSDirectory.open(Paths.get("G:\\Lucene"));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is = new IndexSearcher(reader);
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		/**
		 * title, author, publisher, publishedin和description就是我们需要进行查找的两个字段
		 * 同时在存放索引的时候要使用TextField类进行存放。
		 */
		QueryParser parser = new QueryParser("title", analyzer);
		Query query = parser.parse(q);
		QueryParser parser1 = new QueryParser("author", analyzer);
		Query query1 = parser1.parse(q);
		QueryParser parser2 = new QueryParser("publisher", analyzer);
		Query query2 = parser2.parse(q);
		QueryParser parser3 = new QueryParser("publishedin", analyzer);
		Query query3 = parser3.parse(q);
		QueryParser parser4 = new QueryParser("description", analyzer);
		Query query4 = parser4.parse(q);
		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query3, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query4, BooleanClause.Occur.SHOULD);
		TopDocs hits = is.search(booleanQuery.build(), 1000);
		QueryScorer scorer = new QueryScorer(query);
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		/**
		 * 这里可以根据自己的需要来自定义查找关键字高亮时的样式。
		 */
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		List<InfoBook> infoBookList = new LinkedList<InfoBook>();
		for(ScoreDoc scoreDoc:hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			InfoBook infoBook = new InfoBook();
			infoBook.setId(Long.parseLong(doc.get(("id"))));
			//infoBook.setIsbn(doc.get("isbn"));
			infoBook.setTitle(doc.get(("title")));
			infoBook.setAuthor(doc.get(("author")));
			infoBook.setPublisher(doc.get(("publisher")));
			infoBook.setPublishedin(new SimpleDateFormat("yyyy").parse(doc.get(("publishedin"))));
			infoBook.setDescription(doc.get(("description")));
			String title = doc.get("title");
			String author = doc.get("author");
			String publisher = doc.get("publisher");
			String publishedin = doc.get("publishedin");
			String description = doc.get("description");
			if(title != null) {
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
				String htitle = highlighter.getBestFragment(tokenStream, title);
				if(StringUtil.isEmpty(htitle)) {
					infoBook.setTitle(title);
				} else {
					infoBook.setTitle(htitle);
				}
			}
			if(author != null) {
				TokenStream tokenStream = analyzer.tokenStream("author", new StringReader(author));
				String hauthor = highlighter.getBestFragment(tokenStream, author);
				if(StringUtil.isEmpty(hauthor)) {
					infoBook.setAuthor(author);
				} else {
					infoBook.setAuthor(hauthor);
				}
			}
			if(publisher != null) {
				TokenStream tokenStream = analyzer.tokenStream("publisher", new StringReader(publisher));
				String hpublisher = highlighter.getBestFragment(tokenStream, publisher);
				if(StringUtil.isEmpty(hpublisher)) {
					infoBook.setPublisher(publisher);
				} else {
					infoBook.setPublisher(hpublisher);
				}
			}
			if(publishedin != null) {
				TokenStream tokenStream = analyzer.tokenStream("publishedin", new StringReader(publishedin));
				String hpublishedin = highlighter.getBestFragment(tokenStream, publishedin);
				//System.out.println(hpublishedin+"|"+publishedin);
				if(StringUtil.isEmpty(hpublishedin)) {
					infoBook.setPublishedin(new SimpleDateFormat("yyyy").parse(publishedin));
				} else {
					infoBook.setPublishedin(new SimpleDateFormat("yyyy").parse(publishedin));
				}
			}
			if(description != null) {
				TokenStream tokenStream = analyzer.tokenStream("description", new StringReader(description));
				String hContent = highlighter.getBestFragment(tokenStream, description);
				if(StringUtil.isEmpty(hContent)) {
					if(description.length() <= 200) {
						infoBook.setDescription(description);
					} else {
						infoBook.setDescription(description.substring(0, 200));
					}
					
				} else {
					infoBook.setDescription(hContent);
				}
			}
			infoBookList.add(infoBook);
		}
		return infoBookList;
	}

}
