package ru.webrise.subscription_service.enums;

public enum ServiceName {
    YOUTUBE_PREMIUM("YouTube Премиум"),
    VK_MUSIC("VK Музыка"),
    YANDEX_PLUS("Яндекс Плюс"),
    NETFLIX("Netflix");

    private final String displayName;

    ServiceName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
