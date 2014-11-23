package com.karthik.web;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.karthik.domain.Event;
import com.karthik.persistence.EventDao;

@Controller
public class EventController {
  @Autowired
  EventDao eventDao;
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String ping(ModelMap model) {
    model.addAttribute("time", Calendar.getInstance().getTime());
    eventDao.save(buildEvent());
    return "index";
  }
  private Event buildEvent() {
   Event e = new Event();
   e.name = "log-event";
   e.time=Calendar.getInstance().getTimeInMillis();
   return e;
  }
  @RequestMapping(value="/", method = RequestMethod.POST, headers={"Content-type=application/json"})
  public void event(@RequestBody Event event) {
    eventDao.save(event);
  }
  @RequestMapping(value="/event", method = RequestMethod.GET)
  @ResponseBody
  public String event(@RequestParam("name") String name,@RequestParam("time") long time) {
    return Integer.toString(eventDao.query(name,time));
  }
  
}
