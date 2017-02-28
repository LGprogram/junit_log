package com.kaishengit.dto;

import java.util.List;

/**
 * Created by liu on 2017/2/18.
 */
public class WorkerOutDto {

    /**
     * workerArray : [{"id":"1","workType":"挖掘工","reward":"200","rentNum":"45","total":9000}]
     * fileArray : [{"sourceName":"用友应聘.txt","newName":"039fdbde-cb4f-4b6d-9aad-1f86ee92fba4.txt"}]
     * companyName : 张三的公司
     * tel : 10002
     * rentDate : 2017-02-18
     * linkMan : 张三
     * address : 北京
     * backDate : 2017-03-05
     * cardNum : 410184
     * fax : 1990-10
     * totalDays : 15
     */

    private String companyName;
    private String tel;
    private String rentDate;
    private String linkMan;
    private String address;
    private String backDate;
    private String cardNum;
    private String fax;
    private Integer totalDays;
    private List<WorkerArrayBean> workerArray;
    private List<FileArrayBean> fileArray;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public List<WorkerArrayBean> getWorkerArray() {
        return workerArray;
    }

    public void setWorkerArray(List<WorkerArrayBean> workerArray) {
        this.workerArray = workerArray;
    }

    public List<FileArrayBean> getFileArray() {
        return fileArray;
    }

    public void setFileArray(List<FileArrayBean> fileArray) {
        this.fileArray = fileArray;
    }

    public static class WorkerArrayBean {
        /**
         * id : 1
         * workType : 挖掘工
         * reward : 200
         * rentNum : 45
         * total : 9000
         */

        private Integer id;
        private String workType;
        private float reward;
        private Integer rentNum;
        private float total;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getWorkType() {
            return workType;
        }

        public void setWorkType(String workType) {
            this.workType = workType;
        }

        public float getReward() {
            return reward;
        }

        public void setReward(float reward) {
            this.reward = reward;
        }

        public Integer getRentNum() {
            return rentNum;
        }

        public void setRentNum(Integer rentNum) {
            this.rentNum = rentNum;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }
    }

    public static class FileArrayBean {
        /**
         * sourceName : 用友应聘.txt
         * newName : 039fdbde-cb4f-4b6d-9aad-1f86ee92fba4.txt
         */

        private String sourceName;
        private String newName;

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public String getNewName() {
            return newName;
        }

        public void setNewName(String newName) {
            this.newName = newName;
        }
    }
}
