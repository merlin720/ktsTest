package com.example.ktsdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author merlin720
 * @date 2019-08-04
 * @mail zy44638@gmail.com
 * @description
 */
public class DotsBean implements Serializable {
  private String desc;
  private List<String> dots;

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public List<String> getDots() {
    return dots;
  }

  public void setDots(List<String> dots) {
    this.dots = dots;
  }
}
