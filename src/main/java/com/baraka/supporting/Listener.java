package com.baraka.supporting;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Listener {
    void getUpdate(Update update);
}
