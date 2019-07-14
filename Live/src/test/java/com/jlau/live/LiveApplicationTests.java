package com.jlau.live;

import com.jlau.live.Entity.LiveRoom;
import com.jlau.live.service.LiveRoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ComponentScan
public class LiveApplicationTests {
	@Autowired
	LiveRoomService roomService;
	@Test
	public void contextLoads() {

	}

}
