package com.module.main.voice.web;

import com.module.main.voice.entity.RoomEntity;
import com.module.main.voice.service.IRoomService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/room-api")
public class RoomController {
    @Autowired
    IRoomService iRoomService;

    @GetMapping("/detail")
    @ResponseBody
    public RoomEntity getRoomEntity(@Param("id") Long id){
        return iRoomService.findById(new RoomEntity(id));
    }
}
