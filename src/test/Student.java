package test;


import javax.swing.*;
import java.time.LocalDate;

public class Student {
    private Long ID;
    private String name;
    private String sexual;
    private LocalDate birthday;
    private String classID;
    private Integer chin;
    private Integer math;
    private Integer eng;
    private Integer chem;
    private Integer phys;
    private Integer bio;
    private Integer pol;
    private Integer his;
    private Integer geo;
    private Integer sumScore;

    public Student(Long ID, String name, String sexual,
                   LocalDate birthday, String classID,
                    Integer chin,Integer math, Integer eng,
                   Integer phys,Integer chem,Integer bio,Integer pol,Integer his,Integer geo) {
        this.ID = ID;
        this.name = name;
        this.sexual = sexual;
        this.birthday = birthday;
        this.classID = classID;
        this.math = math;
        this.chin=chin;
        this.chem = chem;
        this.eng=eng;
        this.phys=phys;
        this.geo=geo;
        this.bio=bio;
        this.his = his;
        this.pol=pol;
        this.sumScore = chin+math+eng+phys+chem+bio+pol+his+geo;
    }

    public Student() {
        this(1000L, "新用户", "未知",
                LocalDate.of(1999, 9, 9),
                "未知", 0, 0, 0,0,0,0,0,0,0);
    }

    public static Student getStudent(String[] data) {
        Student e = new Student();
        e.setID(data[0]);
        e.setName(data[1]);
        e.setSexual(data[2]);
        e.setBirthday(data[3]);
        e.setClassID(data[4]);
        e.setChin(data[5]);
        e.setMath(data[6]);
        e.setEng(data[7]);
        e.setPhys(data[8]);
        e.setChem(data[9]);
        e.setBio(data[10]);
        e.setPol(data[11]);
        e.setHis(data[12]);
        e.setGeo(data[13]);
        e.setSumScore();

        return e;
    }

    public Long getID() {
        return ID;
    }
    public void setID(String id) {
        this.ID = Long.parseLong(id);
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSexual() {
        return this.sexual;
    }
    public void setSexual(String sexual) {
        this.sexual = sexual;
    }
    public String getBirthday() {
        return birthday.toString();
    }
    public void setBirthday(String birthday) {
        var tmp = birthday.split("-");
        if (tmp.length != 3) {
            JOptionPane.showMessageDialog(null,
                    "Invalid Value", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            this.birthday = LocalDate.of(Integer.parseInt(tmp[0]),
                    Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
        }
    }

    public String getClassID() {
        return classID;
    }
    public void setClassID(String classID) {
        this.classID = classID;
    }

    public Integer getMath() {
        return math;
    }
    public void setMath(int math) {
        this.math = math;
    }
    public void setMath(String math) {
        this.math = Integer.parseInt(math);
    }

    public Integer getChin() {
        return chin;
    }
    public void setChin(int chin) {
        this.chin = chin;
    }
    public void setChin(String chin) {
        this.chin = Integer.parseInt(chin);
    }

    public Integer getEng() {
        return eng;
    }
    public void setEng(int eng) {
        this.eng = eng;
    }
    public void setEng(String eng) {
        this.eng = Integer.parseInt(eng);
    }

    public Integer getPhys() {
        return phys;
    }
    public void setPhys(int phys) {
        this.phys = phys;
    }
    public void setPhys(String phys) {
        this.phys = Integer.parseInt(phys);
    }

    public Integer getChem() {
        return chem;
    }
    public void setChem(int chem) {this.chem = chem;}
    public void setChem(String chem) {
        this.chem = Integer.parseInt(chem);
    }

    public Integer getBio() {
        return bio;
    }
    public void setBio(int bio) {this.bio = bio;}
    public void setBio(String bio) {this.bio= Integer.parseInt(bio);}

    public Integer getPol() {
        return pol;
    }
    public void setPol(int pol) {this.pol = pol;}
    public void setPol(String pol) {
        this.pol = Integer.parseInt(pol);
    }

    public Integer getHis() {
        return his;
    }
    public void setHis(int his) {this.his = his;}
    public void setHis(String his) {
        this.his = Integer.parseInt(his);
    }

    public Integer getGeo() {
        return geo;
    }
    public void setGeo(int geo) {this.geo = geo;}
    public void setGeo(String geo) {
        this.geo = Integer.parseInt(geo);
    }


    public void setSumScore() {
        this.sumScore = chin+math+eng+phys+chem+bio+pol+his+geo;
    }
    public Integer getSumScore() {
        return sumScore;
    }

    public Object[] getObj() {
        var res = new Object[15];
        res[0] = this.ID;
        res[1] = this.name;
        res[2] = this.sexual;
        res[3] = this.birthday;
        res[4] = this.classID;
        res[5] = this.chin;
        res[6] = this.math;
        res[7] = this.eng;
        res[8]=this.phys;
        res[9]=this.chem;
        res[10]=this.bio;
        res[11]=this.pol;
        res[12]=this.his;
        res[13]=this.geo;
        res[14] = this.sumScore;

        return res;
    }

    public String[] toStringArray() {
        var res = new String[15];
        res[0] = this.ID.toString();
        res[1] = this.name;
        res[2] = this.sexual;
        res[3] = this.birthday.toString();
        res[4] = this.classID;
        res[5] = this.chin.toString();
        res[6] = this.math.toString();
        res[7] = this.eng.toString();
        res[8]=this.phys.toString();
        res[9]=this.chem.toString();
        res[10]=this.bio.toString();
        res[11]=this.pol.toString();
        res[12]=this.his.toString();
        res[13]=this.geo.toString();
        res[14] = this.sumScore.toString();

        return res;
    }
}
