package com.toddway.sandbox;

public class Thing {
    public int ID;
    public String content;
    public String time;
    public String money;
    public String calssfiy;
    public String Isincome;

    public Thing() {
    }

    public Thing(int ID, String content, String time, String money, String calssfiy, String isincome) {
        this.ID = ID;
        this.content = content;
        this.time = time;
        this.money = money;
        this.calssfiy = calssfiy;
        this.Isincome = isincome;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCalssfiy() {
        return calssfiy;
    }

    public void setCalssfiy(String calssfiy) {
        this.calssfiy = calssfiy;
    }

    public String getIsincome() {
        return Isincome;
    }

    public void setIsincome(String isincome) {
        Isincome = isincome;
    }

    @Override
    public String toString() {
        return  "这是一笔"+getIsincome()+"账单，详情："+"\n"+
                "你于时间：" + time + '\n' +
                "增加了一笔账单，账单金额为：" + money + '\n' +
                "账单的使用类别为："+calssfiy+"\n"+
                "账单的备注内容如下：" +'\n'
                + content + '\n' ;
    }
}
