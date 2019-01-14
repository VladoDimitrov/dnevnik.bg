package dnevnik;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import Enums.SortArticlesEnum;
import Exceptions.NoArticleException;
import comparators.ArticleComparators;

public class AllArticles {

	private static Set<Article> allArticles = new HashSet<Article>();

	public static void setAllArticles(Set<Article> allArticles) {
		if (allArticles != null) {
			AllArticles.allArticles = new HashSet<>(allArticles);
			return;
		}
		AllArticles.allArticles = new HashSet<>();
	}

	// Top 5 by category
	public static void previewTop5ByCategory() {
		
		
		String top5ByDate = "Most recent 5: \n\n";
		String top5ByComm = "Top 5 most commented: \n\n";
		String top5ByRead = "Most read 5: \n\n";
		
		top5ByDate += allArticles.stream()
			.sorted(ArticleComparators.byDate()).limit(5)
			.map(article -> article.getPreview())
			.collect(Collectors.joining()) + "\n\n";
		top5ByComm += allArticles.stream()
			.sorted(ArticleComparators.byComment()).limit(5)
			.map(article -> article.getPreview())
			.collect(Collectors.joining()) + "\n\n";
		top5ByRead += allArticles.stream()
			.sorted(ArticleComparators.byRead()).limit(5)
			.map(article -> article.getPreview())
			.collect(Collectors.joining()) + "\n\n";
		
				
		System.out.println(top5ByDate + top5ByComm + top5ByRead);
		
	}

	public static void addArticle(Article article) {
		if (article != null) {
			AllArticles.allArticles.add(article);
		}
	}

	// Search by Article ID
	public static Article findByID(int articleID) {
		for (Article article : allArticles) {
			if (article.getID() == articleID)
				return article;
		}
		return null;
	}

	public static void printAllArticles() {

		allArticles.forEach(article -> System.out.println(article.toString() + "\n\n"));

	}

	public static Set<Article> getAllArticles() {
		return new HashSet<Article>(allArticles);
	}

	public static Set<Article> searchForWord(String wordToFind) throws NoArticleException {
		Set<Article> containingWord = new HashSet<Article>();
		for (Article article : allArticles) {
			if (article.getMainText().contains(wordToFind) || article.getTitle().contains(wordToFind)
					|| article.getTags().contains(wordToFind))
				containingWord.add(article);
		}
		if (containingWord.isEmpty())
			throw new NoArticleException("No article contains this word");
		return containingWord;
	}

	public static Set<Article> searchForWordInText(String wordToFind) throws NoArticleException {
		Set<Article> containingWord = new HashSet<Article>();
		for (Article article : allArticles) {
			if (article.getMainText().contains(wordToFind))
				containingWord.add(article);
		}
		if (containingWord.isEmpty())
			throw new NoArticleException("No article text contains this word");
		return containingWord;
	}

	public static Set<Article> searchForWordInTitle(String wordToFind) throws NoArticleException {
		Set<Article> containingWord = new HashSet<Article>();
		for (Article article : allArticles) {
			if (article.getTitle().contains(wordToFind))
				containingWord.add(article);
		}
		if (containingWord.isEmpty())
			throw new NoArticleException("No article title contains this word");
		return containingWord;
	}

	public static Set<Article> searchForWordInTags(String wordToFind) throws NoArticleException {
		Set<Article> containingWord = new HashSet<Article>();
		for (Article article : allArticles) {
			if (article.getTags().contains(wordToFind))
				containingWord.add(article);
		}
		if (containingWord.isEmpty())
			throw new NoArticleException("No article contains this tag");
		return containingWord;
	}

	public static Set<Article> getAllNotUsersArticles() throws NoArticleException {
		Set<Article> articles = new HashSet<Article>();
		for (Article x : allArticles) {
			if (x.getAuthor().getUsername().equals("Dnevnik.bg")) {
				articles.add(x);
			}
		}
		if (articles.isEmpty())
			throw new NoArticleException("No articles that are not Users' are in this site ");
		return articles;
	}

	// public Set<Article> getAllArticlesFromToday() {
	// Set<Article> articles = new HashSet<Article>();
	// for (Article x : AllArticles.allArticles) {
	// if(x.getDateAdded().get(Calendar.DAY_OF_MONTH)==(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)))
	// {
	//
	// }
	// }
	// return articles;
	// }

	public static Set<Article> sortArticles(SortArticlesEnum byWhat) {
		Set<Article> sortedArticles = new TreeSet<Article>();
		switch (byWhat) {
		case BYREADCOUNT:
			sortedArticles = new TreeSet<Article>(ArticleComparators.byRead());
			break;
		case BYDATEADDED:
			sortedArticles = new TreeSet<Article>(ArticleComparators.byDate());
			break;
		case BYCOMMENTSCOUNT:
			sortedArticles = new TreeSet<Article>(ArticleComparators.byComment());
			break;
		default:
			break;
		}
		sortedArticles.addAll(allArticles);
		return sortedArticles;
	}

	
}
