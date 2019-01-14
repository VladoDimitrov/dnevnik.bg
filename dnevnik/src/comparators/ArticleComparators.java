package comparators;

import java.util.Comparator;

import dnevnik.Article;

public class ArticleComparators {
	private static Comparator<Article> byReadCount = new Comparator<Article>() {
		@Override
		public int compare(Article a1, Article a2) {
			return a1.getReadCount() > a2.getReadCount() ? -1 : a2.getReadCount() > a1.getReadCount() ? 1 : 0;
		}
	};
	private static Comparator<Article> byDateAdded = new Comparator<Article>() {
		@Override
		public int compare(Article a1, Article a2) {
			return (!a2.getDateAdded().before(a1.getDateAdded()) ? 1 : -1);
		}
	};
	private static Comparator<Article> byCommentsCount = new Comparator<Article>() {
		@Override
		public int compare(Article a1, Article a2) {
			return a1.getComments().size() > a2.getComments().size() ? -1
					: a2.getComments().size() > a1.getComments().size() ? 1 : 0;
		}
	};
	public static Comparator<Article> byRead() {
		return byReadCount;
	}
	public static Comparator<Article> byDate() {
		return byDateAdded;
	}
	public static Comparator<Article> byComment() {
		return byCommentsCount;
	}
}
