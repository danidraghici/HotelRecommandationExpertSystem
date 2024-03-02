class Hotel {
    String name, city, roomType, zone, location;
    int pricePerNight, stars;
    boolean hasRestaurant, wellnessCenter, petFriendly, parking;
    // Constructor
    public Hotel(String[] data) {
        this.name = data[0];
        this.city = data[1];
        this.pricePerNight = Integer.parseInt(data[2]);
        this.roomType = data[3];
        this.hasRestaurant = data[4].equals("Yes");
        this.zone = data[5];
        this.location = data[6];
        this.wellnessCenter = data[7].equals("Yes");
        this.petFriendly = data[8].equals("Yes");
        this.stars = Integer.parseInt(data[9]);
        this.parking = data[10].equals("Yes");
    }
    @Override
    public String toString() {
        return name + " in " + city + " - " + roomType + ", Price: " + pricePerNight;
    }
}
