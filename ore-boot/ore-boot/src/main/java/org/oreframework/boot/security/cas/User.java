package org.oreframework.boot.security.cas;

public class User {

	private String jsessionid;
	private Integer id;
	private String username;
	private String realname;
	private Integer isadmin;
	private String mobile;
	private String email;
	private String roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

    public String getJsessionid() {
		return jsessionid;
	}

	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}


    /**
     * 得到属性字符串
     * @return String 属性字符串
     */
    public String toString()
    {
        String line = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer("{");
        sb.append(line);
        sb.append("id=").append((this.id)).append(line);
        sb.append("username=").append((this.username)).append(line);
        sb.append("realname=").append((this.realname)).append(line);
        sb.append("isadmin=").append((this.isadmin)).append(line);
        sb.append("mobile=").append((this.mobile)).append(line);
        sb.append("email=").append((this.email)).append(line);
        sb.append("roles=").append((this.roles)).append(line);
        sb.append("jsessionid=").append((this.jsessionid)).append(line);
        sb.append("}");
        return sb.toString();
    }

}