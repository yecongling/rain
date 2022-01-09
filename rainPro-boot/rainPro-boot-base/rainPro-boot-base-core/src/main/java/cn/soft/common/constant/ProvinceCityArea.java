package cn.soft.common.constant;

import cn.soft.common.util.oConvertUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 省市区
 */
@Component("pca")
public class ProvinceCityArea {

    List<Area> areaList;

    /**
     * 获取去油
     *
     * @param code /
     * @return /
     */
    public String getText(String code) {
        this.initAreaList();
        if (this.areaList != null || this.areaList.size() > 0) {
            List<String> ls = new ArrayList<>();
            getAreaByCode(code, ls);
            return String.join("/", ls);
        }
        return "";
    }

    /**
     * 根据text获取编码
     *
     * @param text /
     * @return /
     */
    public String getCode(String text) {
        this.initAreaList();
        if (areaList != null || areaList.size() > 0) {
            for (int i = areaList.size() - 1; i >= 0; i--) {
                if (text.contains(areaList.get(i).getText())) {
                    return areaList.get(i).getId();
                }
            }
        }
        return null;
    }

    /**
     * 通过code获取区域
     *
     * @param code /
     * @param ls   /
     */
    public void getAreaByCode(String code, List<String> ls) {
        for (Area area : areaList) {
            if (area.getId().equals(code)) {
                String pid = area.getPid();
                ls.add(0, area.getText());
                getAreaByCode(pid, ls);
            }
        }
    }


    /**
     * 初始化地域list
     */
    private void initAreaList() {
        if (this.areaList == null || this.areaList.size() == 0) {
            this.areaList = new ArrayList<>();
            try {
                String jsonData = oConvertUtils.readStatic("classpath:static/pca.json");
                JSONObject baseJson = JSONObject.parseObject(jsonData);
                //第一层 省
                JSONObject provinceJson = baseJson.getJSONObject("86");
                for (String provinceKey : provinceJson.keySet()) {
                    Area province = new Area(provinceKey, provinceJson.getString(provinceKey), "86");
                    this.areaList.add(province);
                    //第二层 市
                    JSONObject cityJson = baseJson.getJSONObject(provinceKey);
                    for (String cityKey : cityJson.keySet()) {
                        Area city = new Area(cityKey, cityJson.getString(cityKey), provinceKey);
                        this.areaList.add(city);
                        //第三层 区
                        JSONObject areaJson = baseJson.getJSONObject(cityKey);
                        if (areaJson != null) {
                            for (String areaKey : areaJson.keySet()) {
                                Area area = new Area(areaKey, areaJson.getString(areaKey), cityKey);
                                this.areaList.add(area);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取json文件
     * @param file /
     * @return /
     */
    private String jsonRead(File file){
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception ignored) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

    /**
     * 区域类
     */
    class Area {
        String id;
        String text;
        String pid;

        public Area(String id, String text, String pid) {
            this.id = id;
            this.text = text;
            this.pid = pid;
        }

        public String getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public String getPid() {
            return pid;
        }
    }
}
