package peaksoft.request;



public record MenuRequest(

        String subCategoryName,
        String name,
        String menuName,
        String image,
        int price,
        String description,
        Boolean isVegetarian

) {
}
