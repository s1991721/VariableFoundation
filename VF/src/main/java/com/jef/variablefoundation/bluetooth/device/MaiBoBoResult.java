package com.jef.variablefoundation.bluetooth.device;

/**
 * Created by mr.lin on 2019/3/15
 * 脉搏波结果
 */
public class MaiBoBoResult {

    public static final int RESULT_TYPE_LINKED=0;
    public static final int RESULT_TYPE_PROCESSING=1;
    public static final int RESULT_TYPE_FINAL=2;

    private int type;

    private int mmHg;
    private int SYS;
    private int DIA;
    private int PUL;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMmHg() {
        return mmHg;
    }

    public void setMmHg(int mmHg) {
        this.mmHg = mmHg;
    }

    public int getSYS() {
        return SYS;
    }

    public void setSYS(int SYS) {
        this.SYS = SYS;
    }

    public int getDIA() {
        return DIA;
    }

    public void setDIA(int DIA) {
        this.DIA = DIA;
    }

    public int getPUL() {
        return PUL;
    }

    public void setPUL(int PUL) {
        this.PUL = PUL;
    }
}
