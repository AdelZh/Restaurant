package peaksoft.request;



public record MenuRequest(

        String subCategoryName,
        String restaurantName,
        String name,
        String image,
        int price,
        String description,
        Boolean isVegetarian

) {
}
