package com.letussolve.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.letussolve.models.Admin;
import com.letussolve.models.Answer;
import com.letussolve.models.Category;
import com.letussolve.models.Question;
import com.letussolve.models.Subject;
import com.letussolve.models.User;
import com.letussolve.utils.LetUsSolveUtil;

public class DBOps extends DBConnect {
	
	public boolean save(User user) throws SQLException {
		String sql = "INSERT INTO lus_users (first_name, last_name, email, mobile, username, password, created_at, updated_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, user.getFirstName());
		stmt.setString(2, user.getLastName());
		stmt.setString(3, user.getEmail());
		stmt.setString(4, user.getMobile());
		stmt.setString(5, user.getUsername());
		stmt.setString(6, user.getPassword());
		stmt.setString(7, LetUsSolveUtil.getDateString(user.getCreatedAt()));
		stmt.setString(8, LetUsSolveUtil.getDateString(user.getUpdatedAt()));
		return 1 == stmt.executeUpdate();
	}

	public User getUserByUsername(String username) throws SQLException {
		final String METHOD_NAME = "<DBOps::getUserByUsername()>";
		System.out.println(METHOD_NAME);
		
		User user = null;
		String sql = "SELECT * FROM lus_users WHERE username=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, username);	
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			user = buildUserFromResultSet(rs);
		}
		return user;
	}

	private User buildUserFromResultSet(ResultSet rs) throws SQLException {
		final String METHOD_NAME = "<DBOps::buildUserFromResultSet()>";
		System.out.println(METHOD_NAME);
		
		User user = new User();
		user.setUserID(rs.getInt("id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setEmail(rs.getString("email"));
		user.setMobile(rs.getString("mobile"));
		user.setPassword(rs.getString("password"));
		user.setActive(rs.getBoolean("active"));
		user.setCreatedAt(rs.getDate("created_at"));
		user.setUpdatedAt(rs.getDate("updated_at"));
		return user;
	}

	public Admin getAdminByUsername(String username) throws SQLException {
		final String METHOD_NAME = "<DBOps::getAdminByUsername()>";
		System.out.println(METHOD_NAME);
		
		Admin admin = null;
		String sql = "SELECT * FROM lus_admins WHERE username=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			admin = buildAdminFromResultSet(username, rs);
		}
		return admin;
	}

	private Admin buildAdminFromResultSet(String username, ResultSet rs) throws SQLException {
		Admin admin = new Admin();
		admin.setAdminId(rs.getInt("id"));
		admin.setName(rs.getString("name"));
		admin.setPassword(rs.getString("password"));
		admin.setUsername(username);
		admin.setCreatedAt(rs.getDate("created_at"));
		admin.setUpdatedAt(rs.getDate("updated_at"));
		return admin;
	}

	public boolean save(Category category) throws SQLException {
		final String METHOD_NAME = "<DBOps::save()::Category>";
		String sql = "INSERT INTO lus_categories (slug, name, description) "
				+ "VALUES (?,?,?)";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, category.getSlug());
		stmt.setString(2, category.getName());
		stmt.setString(3, category.getDescription());
		return 1 == stmt.executeUpdate();
		
	}

	public boolean save(Subject sub) throws SQLException {
		final String METHOD_NAME = "<DBOps::save()::Subject>";
		System.out.println(METHOD_NAME);
		
		String sql = "INSERT INTO lus_subjects (name, description, cat_id) "
				+ "VALUES (?, ?, ?)";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, sub.getName());
		stmt.setString(2, sub.getDescription());
		stmt.setString(3, sub.getCat());
		return 1 == stmt.executeUpdate();
	}

	public List<Category> getAllCategories() throws SQLException {
		final String METHOD_NAME = "<DBOps::getAllCategories()>";
		System.out.println(METHOD_NAME);
		
		String sql = "SELECT * FROM lus_categories";
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Category> categories = new ArrayList<Category>();
		while (rs.next()) {
			Category cat = new Category();
			cat.setCatId(rs.getInt("id"));
			cat.setName(rs.getString("name"));
			cat.setDescription(rs.getString("description"));
			cat.setSlug(rs.getString("slug"));
			categories.add(cat);
		}
		return categories;
	}

	public List<Subject> getAllSubjectsByCategoryId(String catId) throws SQLException {
		final String METHOD_NAME = "<DBOps::getAllSubjectsByCategoryId()>";
		System.out.println(METHOD_NAME);
		
		String sql = "SELECT * FROM lus_subjects";
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Subject> subjects = new ArrayList<>();
		JsonParser jsonParser = new JsonParser();
		while(rs.next()) {
			Subject sub = buildSubjectFromResultSet(rs);
			JsonArray entries = (JsonArray) jsonParser.parse(rs.getString("cat_id")); 
			for (int i=0; i < entries.size(); i++) {
				System.out.println("Category IDs from database: " + entries.get(i).getAsString());
				if (entries.get(i).getAsString().equals(catId)) {
					System.out.println("Category IDs entries from database that matches subject catId: " + entries.get(i).getAsString());
					subjects.add(sub);
				}
			}
		}
		return subjects;
	}

	public boolean update(Category cat) throws SQLException {
		final String METHOD_NAME = "<DBOps::update::Category>";
		System.out.println(METHOD_NAME);
		
		String sql = "UPDATE lus_categories SET name=? WHERE id=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, cat.getName());
		stmt.setInt(2, cat.getCatId());
		return 1 == stmt.executeUpdate();
	}

	public boolean delete(Category cat) throws SQLException {
		final String METHOD_NAME = "<DBOps::delete()::Category>";
		System.out.println(METHOD_NAME);
		
		String sql = "DELETE FROM lus_categories WHERE id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, cat.getCatId());
		return 1 == stmt.executeUpdate();
	}

	public List<Subject> getAllSubjects() throws SQLException {
		final String METHOD_NAME = "<DBOps::getAllSubjects()>";
		System.out.println(METHOD_NAME);
		
		String sql = "SELECT * FROM lus_subjects";
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Subject> subjects = new ArrayList<>();
		while (rs.next()) {
			subjects.add(buildSubjectFromResultSet(rs));
		}
		return subjects;
	}

	private Subject buildSubjectFromResultSet(ResultSet rs) throws SQLException {
		Subject sub = new Subject();
		sub.setSubjectId(rs.getInt("id"));
		sub.setName(rs.getString("name"));
		sub.setDescription(rs.getString("description"));
		sub.setCat(rs.getString("cat_id"));
		return sub;
	}

	public boolean update(Subject sub) throws SQLException {
		final String METHOD_NAME = "<DBOps::update()::Subject>";
		System.out.println(METHOD_NAME);
		
		String sql = "UPDATE lus_subjects SET name=? WHERE id=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, sub.getName());
		stmt.setInt(2, sub.getSubjectId());
		return 1 == stmt.executeUpdate();
	}

	public boolean delete(Subject sub) throws SQLException {
		final String METHOD_NAME = "<DBOps::delete()::Subject>";
		System.out.println(METHOD_NAME);
		
		String sql = "DELETE FROM lus_subjects WHERE id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, sub.getSubjectId());
		return 1 == stmt.executeUpdate();
	}

	public boolean save(Question q) throws SQLException {
		final String METHOD_NAME = "<DBOps::save()::Question>";
		System.out.println(METHOD_NAME);
		
		String sql = "INSERT INTO lus_questions (question, subject) "
				+ "VALUES (?, ?)";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, q.getQuestion());
		stmt.setInt(2, q.getSubject().getSubjectId());
		return 1 == stmt.executeUpdate();
	}

	public List<Question> getAllQuestions() throws SQLException {
		final String METHOD_NAME = "<DBOps::getAllQuestions()>";
		System.out.println(METHOD_NAME);
		
		List<Question> questions = new ArrayList<>();
		String sql = "SELECT * FROM lus_questions";
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			questions.add(buildQuestionFromResultSet(rs));
		}
		return questions;
	}
	
	public List<Question> getAllQuestionsBySubjectId(String subId) throws SQLException {
		final String METHOD_NAME = "<DBOps::getAllQuestionsBySubjectId()>";
		System.out.println(METHOD_NAME);
		
		String sql = "SELECT * FROM lus_questions WHERE subject=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, Integer.parseInt(subId));
		ResultSet rs = stmt.executeQuery();
		List<Question> questions = new ArrayList<>();
		
		while (rs.next()) {
			Question q = new Question();
			q.setqId(rs.getInt("id"));
			q.setQuestion(rs.getString("question"));
			Answer rightAnswer = new Answer();
			rightAnswer.setAnsId(rs.getInt("right_answer"));
			q.setRightAnswer(rightAnswer );
			Subject subject = new Subject();
			subject.setSubjectId(rs.getInt("subject"));
			q.setSubject(subject );
			questions.add(q);
		}
		return questions;
	}

	private Question buildQuestionFromResultSet(ResultSet rs) throws SQLException {
		final String METHOD_NAME = "<DBOps::buildQuestionFromResultSet()>";
		System.out.println(METHOD_NAME);
		
		Question q = new Question();
		q.setqId(rs.getInt("id"));
		q.setQuestion(rs.getString("question"));
		Answer ans = new Answer();
		ans.setAnsId(rs.getInt("right_answer"));
		q.setRightAnswer(ans);
		Subject sub = new Subject();
		sub.setSubjectId(rs.getInt("subject"));
		q.setSubject(sub);
		
		System.out.println(q);
		return q;
	}

	public int save(Answer ans) throws SQLException {
		final String METHOD_NAME = "<DBOps::save()::Answer>";
		System.out.println(METHOD_NAME);
		
		String sql = "INSERT INTO lus_answers (question_id, answer) "
				+ "VALUES (?,?) ";
		stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, ans.getQ().getqId());
		stmt.setString(2, ans.getAnswer());
		ResultSet rs = null;
		if (1 == stmt.executeUpdate()) {
			rs = stmt.getGeneratedKeys();
		}
		int ansId = 0;
		if (rs.next()) {
			ansId = rs.getInt(1);
		}
		con.commit();
		return ansId;
	}

	public boolean save(List<Answer> answersList) throws SQLException {
		final String METHOD_NAME = "<DBOps::save()::AnswerList>";
		System.out.println(METHOD_NAME);
		
		String sql = "INSERT INTO lus_answers (question_id, answer) "
				+ "VALUES (?,?) ";
		con.setAutoCommit(false);
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, answersList.get(0).getQ().getqId());
		stmt.setString(2, LetUsSolveUtil.toJson(answersList));
		return 1 == stmt.executeUpdate();
	}
	
	public List<Answer> getAllAnswers() throws SQLException {
		final String METHOD_NAME = "<DBOps::getAllAnswers()>";
		System.out.println(METHOD_NAME);
		
		List<Answer> answers = new ArrayList<>();
		String sql = "SELECT * FROM lus_answers";
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Answer ans = new Answer();
			ans.setAnsId(rs.getInt("id"));
			Question q = new Question();
			q.setqId(rs.getInt("question_id"));
			ans.setQ(q );
			ans.setAnswer(rs.getString("answer"));
			answers.add(ans);
		}
		return answers;
	}
	
	public List<Answer> getAllAnswersByQuestionId(String qId) throws SQLException {
		final String METHOD_NAME = "<DBOps::getAllAnswers()>";
		System.out.println(METHOD_NAME);
		
		List<Answer> answers = new ArrayList<>();
		String sql = "SELECT * FROM lus_answers WHERE question_id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, Integer.parseInt(qId));
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Answer ans = new Answer();
			ans.setAnsId(rs.getInt("id"));
			Question q = new Question();
			q.setqId(rs.getInt("question_id"));
			ans.setQ(q );
			ans.setAnswer(rs.getString("answer"));
			answers.add(ans);
		}
		return answers;
	}

	public boolean update(Question q) throws SQLException {
		final String METHOD_NAME = "<DBOps::update()::Question>";
		System.out.println(METHOD_NAME);
		
		con.setAutoCommit(true);
		String sql = "UPDATE lus_questions SET right_answer=? WHERE id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, q.getRightAnswer().getAnsId());
		stmt.setInt(2, q.getqId());
		boolean flag = 1 == stmt.executeUpdate();
		return flag;
	}

	public boolean delete(Answer rightAnswer) throws SQLException {
		final String METHOD_NAME = "<DBOps::delete()>";
		System.out.println(METHOD_NAME);
		
		String sql = "DELETE FROM lus_answers WHERE question_id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, rightAnswer.getQ().getqId());
		return 1 == stmt.executeUpdate();
	}

	public Question getQuestionById(String parameter) throws SQLException {
		final String METHOD_NAME = "<DBOps::getQuestionById()>";
		System.out.println(METHOD_NAME);
		
		String sql = "SELECT * FROM lus_questions WHERE id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, Integer.parseInt(parameter));
		ResultSet rs = stmt.executeQuery();
		
		Question q = new Question();
		if (rs.next()) {
			q.setqId(Integer.parseInt(parameter));
			q.setQuestion(rs.getString("question"));
			
			Answer ans = new Answer();
			ans.setAnsId(rs.getInt("right_answer"));
			
			q.setRightAnswer(ans);
			
			Subject sub = new Subject();
			sub.setSubjectId(rs.getInt("subject"));
			
			q.setSubject(sub);
		}
		return q;
	}

	public List<Answer> getAnswersByQuestionId(String qId) throws SQLException {
		final String METHOD_NAME = "<DBOps::getAnswersByQuestionId()>";
		System.out.println(METHOD_NAME);
		
		List<Answer> answers = new ArrayList<>();
		String sql = "SELECT * FROM lus_answers WHERE question_id=?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, Integer.parseInt(qId));
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Answer ans = new Answer();
			ans.setAnsId(rs.getInt("id"));
			ans.setAnswer(rs.getString("answer"));
			
			Question q = new Question();
			q.setqId(rs.getInt("question_id"));
			ans.setQ(q);
			
			answers.add(ans);
		}
		
		return answers;
	}
}
