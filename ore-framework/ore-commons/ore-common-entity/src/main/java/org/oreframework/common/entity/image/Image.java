package org.oreframework.common.entity.image;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/** 
 * @author  huangzz
 * @version  [1.0.0, 2015-3-23]
 */
public class Image implements Serializable {

    private static final long serialVersionUID = -8664948859289975981L;
    private Long imageId;
    private String itemId;
    private String imagePath;
    private Integer type;
    private Date gmtCreate;
	private String imageUrl;
	private String imageUrlTwo;
    private Integer orderNum;

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setImageUrlTwo(String imageUrlTwo) {
		this.imageUrlTwo = imageUrlTwo;
	}

	public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    public String getImageUrl() {
        String subItemImage = "";
        if (StringUtils.isNotBlank(imagePath)) {
            subItemImage = imagePath.replace(".", ".100x75.");
        }
        return subItemImage;
    }

    public String getImageUrlTwo() {
        String subItemImage = "";
        if (StringUtils.isNotBlank(imagePath)) {
            subItemImage = imagePath.replace(".", ".112x84.");
        }
        return subItemImage;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
