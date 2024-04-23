
public class ViewDTO {
	private String category;
	private String searchType;
	private String searchWord;
	private String sortType;
	private int pageNumber;
	
	public ViewDTO(){}
	public ViewDTO(String category, String searchType, String searchWord, String sortType, int pageNumber) {
		super();
		this.category = category;
		this.searchType = searchType;
		this.searchWord = searchWord;
		this.sortType = sortType;
		this.pageNumber = pageNumber;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
