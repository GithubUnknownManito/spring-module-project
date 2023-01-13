package com.module.main.voice.web;

import com.module.main.voice.entity.RoomEntity;
import com.module.main.voice.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room-api")
public class RoomController {
    @Autowired
    IRoomService iRoomService;

    @GetMapping("/{id}")
    public RoomEntity getRoomEntity(@PathVariable Long id){
        return iRoomService.findById(new RoomEntity(id));
    }
}
