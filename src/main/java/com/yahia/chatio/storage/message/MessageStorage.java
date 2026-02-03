package com.yahia.chatio.storage.message;

import java.util.List;


public interface MessageStorage {
    void save(String message); //NOTE: message can be pic, file, not just string
    List<String> load();
}
