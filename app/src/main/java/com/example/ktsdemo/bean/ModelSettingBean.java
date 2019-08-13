package com.example.ktsdemo.bean;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019-08-09
 * @mail zy44638@gmail.com
 * @description
 */
public class ModelSettingBean implements Serializable {

  private String name;
  public boolean selected;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
