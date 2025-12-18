package com.yahia.anotherchatapplicatoin.protocol;


//TODO: separate into ServerMessage and ClientMessage
public sealed interface Message permits
    BroadCastMessage, PrivateMessage,
    HandShakeRequest, HandShakeResponse,
    DisconnectRequest,
    ServerStatus{}
