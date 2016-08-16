package com.mrlzy.shiro.weixin.bean;


import java.util.ArrayList;
import java.util.List;

public class WeiXinMenu {

     private  String name;

     private MenuType  type=MenuType.View;

     private String key;

     private  String url;

     private  String media_id;

     private List<WeiXinMenu> sub_button;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type==null?null: type.toString();
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }


    public List<WeiXinMenu> getSub_button() {
        return sub_button;
    }

    public  void addSubMenu(WeiXinMenu m){
        this.sub_button.add(m);
    }

    public void setSub_button(List<WeiXinMenu> sub_button) {
        this.sub_button = sub_button;
    }





   public static enum MenuType{
        Click("click"), View("view"), ScancodePush("scancode_push"), ScancodeWaitmsg("scancode_waitmsg"),
        PicSysphoto("pic_sysphoto"), PicPhotoOrAlbum("pic_photo_or_album"), PicWeixin("pic_weixin"),
        LocationSelect("location_select"), MediaId("media_id"),ViewLimited("view_limited");

       private  String value;

       MenuType(String value) {
           this.value = value;
       }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
