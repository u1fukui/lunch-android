package com.u1fukui.lunch.app.model;

import java.io.Serializable;

/**
 * お店データ
 */
public class SLRestaurant implements Serializable {

  /** 識別ID */
  public String id;

  /** 店名 */
  public String name;

  /** 住所 */
  public String address;

  /** オススメメニュー */
  public String featuredMenu;

  /** お昼の営業開始時間 */
  public String startLunchTime;

  /** お昼の営業修了時間 */
  public String finishLunchTime;

  /** 定休日 */
  public String holiday;

  /** 食べログURL */
  public String tabelogUrl;

  /** 一言コメント */
  public String comment;

  /** サムネイル画像のファイル名 */
  public String thumbnailName;

  /** 緯度 */
  public double lat;

  /** 軽度 */
  public double lng;

  /** 現在地からの距離 */
  public double distance;
}
