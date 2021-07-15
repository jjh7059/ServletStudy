package xyz.itwill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xyz.itwill.dto.PhonebookDTO;

//DAO(Data Access Object) Ŭ���� : ���̺��� �࿡ ���� ����,����,����,�˻� ����� �����ϴ� Ŭ����
// => �̱��� Ŭ������ �ۼ��ϴ� ���� ����

//PHONEBOOK ���̺��� ������ ����,����,����,�˻� ����� �����ϴ� Ŭ����
public class PhonebookDAO extends JdbcDAO {
	private static PhonebookDAO _dao;
	
	private PhonebookDAO() {
		// TODO Auto-generated constructor stub
	}
	
	static {
		_dao=new PhonebookDAO();
	}
	
	public static PhonebookDAO getDAO() {
		return _dao;
	}
	
	//PHONEBOOK ���̺��� ����� ��� ���� �˻��Ͽ� ��ȯ�ϴ� �޼ҵ�
	public List<PhonebookDTO> selectAllPhonebook() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<PhonebookDTO> phonebookList=new ArrayList<PhonebookDTO>();
		try {
			con=getConnection();
			
			String sql="select * from phonebook order by phone";
			pstmt=con.prepareStatement(sql);

			rs=pstmt.executeQuery();
			
			//�˻� ����� Java �ڷ������� ���� ó��
			while(rs.next()) {
				PhonebookDTO phonebook=new PhonebookDTO();
				phonebook.setPhone(rs.getString("phone"));
				phonebook.setName(rs.getString("name"));
				phonebook.setAddress(rs.getString("address"));
				phonebookList.add(phonebook);				
			}
		} catch (SQLException e) {
			System.out.println("[����]selectAllPhonebook �޼ҵ��� SQL ���� = "+e.getMessage());
		} finally {
			close(con, pstmt, rs);
		} 
		return phonebookList;
	}
}








