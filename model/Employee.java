package xupt.se.ttms.model;

import java.io.Serializable;

public class Employee implements Serializable {

	//权限0表示管理员 1表示经理, 2表示售票员
	private int access;
	private int id;
	private String name;
	private String password;
	private String cName;
	private String tel;
	private int saleMoney ;
//	int saleMoney ;

	public int getEmp_no() {
		return emp_no;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"access=" + access +
				", id=" + id +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", cName='" + cName + '\'' +
				", tel='" + tel + '\'' +
				", saleMoney=" + saleMoney +
				", emp_no=" + emp_no +
				'}';
	}

	public void setEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}
	int emp_no ;


	public int getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(int saleMoney) {
		this.saleMoney = saleMoney;
	}
	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Employee(){
		
	}

	public Employee(int access, int id, String name, String password) {
		this.access = access;
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public int getAccess() {
		return access;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void showValue() {
		System.out.println("编号：" + id + "\t姓名：" + name);
	}

}
