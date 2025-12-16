package com.yahia.anotherchatapplicatoin.protocol;

public sealed interface Message permits
    BroadCastMessage, PrivateMessage,
    HandShakeRequest, HandShakeResponse,
    DisconnectRequest,
    ServerStatus{}
