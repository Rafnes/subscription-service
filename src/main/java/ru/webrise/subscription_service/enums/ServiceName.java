package ru.webrise.subscription_service.enums;

import ru.webrise.subscription_service.exception.IllegalNameException;

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

    public static ServiceName fromString(String name) {
        for (ServiceName s : ServiceName.values()) {
            if (s.getDisplayName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new IllegalNameException("Неизвестный тип подписки: " + name);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
