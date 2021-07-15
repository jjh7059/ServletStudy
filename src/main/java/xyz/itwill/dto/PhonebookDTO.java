package xyz.itwill.dto;

//DTO(Data Transfer Object) 클래스 : 테이블의 행정보를 저장하여 전달하기 위한 클래스

/*
이름      널?       유형           
------- -------- ------------ 
PHONE   NOT NULL VARCHAR2(20) 
NAME             VARCHAR2(20) 
ADDRESS          VARCHAR2(50) 
 */

//PHONEBOOK 테이블의 행정보를 저장하기 위한 클래스
public class PhonebookDTO {
	private String phone;
	private String name;
	private String address;
	
	public PhonebookDTO() {
		// TODO Auto-generated constructor stub
	}

	public PhonebookDTO(String phone, String name, String address) {
		super();
		this.phone = phone;
		this.name = name;
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
