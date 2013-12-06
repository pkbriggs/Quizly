<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="QuizSummary"/>
  <jsp:param name = "cssInclude" value = "css/quiz-summary.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>
<%@ page import="dbconnection.DBConnection, java.util.*, java.sql.*, quizzes.Quiz, java.util.Date, java.text.SimpleDateFormat" %>
	<script src="js/QuizSummary.js"></script>
	<% 
	
		/*
		 * Gets the correct quiz and its values
		 */
		ServletContext sContext = getServletContext();
		DBConnection db = DBConnection.getInstance();
		//String quizID = request.getParameter("id");
		String quizID = "1";
		int quizIDInt = Integer.parseInt(quizID);
		Quiz quiz = new Quiz(quizIDInt);
		//String user = (String)session.getAttribute("username");
		String user = "stevenqian";
		String name = quiz.getTitle();
		String creator = quiz.getCreator();
		String dateCreated = quiz.getDateCreated();
		String description = quiz.getDescription();

		
		/*
		 * Class designed to keep track of an attempt
		 */
		class Attempt {
			String user;
			String score;
			String time;
			String date;
			Attempt() {
				//nothing
			}
			public void setUser(String user) {
				this.user = user;
			}
			public void setScore(String score) {
				this.score = score;
			}
			public void setTime(String time) {
				this.time = time;
			}
			public void setDate(String date) {
				this.date = date;
			}
			public double getScore() {
				return Double.parseDouble(score); 
			}
			public String percentScore() {
				double tempScore = Double.parseDouble(score);
				tempScore *= 100;
				String toReturn = Double.toString(tempScore);
				int i = 0;
				while (toReturn.charAt(i) != '.') {
					i++;
				}
				toReturn = toReturn.substring(0,i) + " %";
				return toReturn;
			}
			public String getDate() {
				String toReturn = "";
				try {
					final String old = "yyyy-MM-dd HH:mm:ss";
					final String newForm = "MMM dd yyyy";
			
					String oldString = date;
					String newString;
			
					SimpleDateFormat simpleFormat = new SimpleDateFormat(old);
					Date oldDate = simpleFormat.parse(oldString);
					simpleFormat.applyPattern(newForm);
					return simpleFormat.format(oldDate);
				} catch (Exception e) {/*ignore*/}
				return toReturn;
			}

		}
		
		/*
		 *	Comparator that compares by date 
		 */
		class DateCompare implements Comparator<Attempt> {
			@Override
			public int compare(Attempt a1, Attempt a2) {
				try {
					Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(a1.date);
					Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(a2.date);
					if (date1.after(date2)) return -1;
					else return 1;
				} catch (Exception ie) {/*ignore*/}
				return 0;
				
			}
		}
		/*
		 *	Comparator that compares by score
		 */
		class ScoreCompare implements Comparator<Attempt> {
			@Override
			public int compare(Attempt a1, Attempt a2) {
				double order = a2.getScore() - a1.getScore();
				if (order < 0) return -1;
				else if (order > 0) return 1;
				else return 0;
			}
		}
		
		/*
		 *	Comparator that compare by time
		 */
		class TimeCompare implements Comparator<Attempt> {
			@Override
			public int compare(Attempt a1, Attempt a2) {
				int time1 = Integer.parseInt(a1.time);
				int time2 = Integer.parseInt(a2.time);
				return time1 - time2;
			}
		}
		 
		List<Attempt> attempts = new Vector<Attempt>(); //vector of all attempts for this quiz
		ResultSet rs2 = db.executeQuery("SELECT * FROM scores");
		while (rs2.next()) {
			if (rs2.getString("quizID").equals(quizID)) {
				Attempt curr = new Attempt();
				curr.setUser(rs2.getString("username"));
				curr.setScore(rs2.getString("score"));
				curr.setTime(rs2.getString("time"));
				curr.setDate(rs2.getString("dateTaken"));
				attempts.add(curr);
			}
		}
		
		
	%>
	<h2 id ="title"><%out.println(quiz.getTitle());%></h2>
	<a id="descriptionToggle">+ description</a><br><br>
	<% 
		out.println("<div id=\"description\"><p>" + quiz.getDescription() + "</p></div>");
	%>
	<p>by <a href='profile.jsp?id=<%= User.getIDFromUsername(user)%>' > <%out.println(user);%></a></p><br><br>
	<button class="categoryButton btn" id="yourButton">Your Performances</button>
	<button class="categoryButton btn" id="allPerformanceButton">All Time Best</button>
	<button class="categoryButton btn" id="todayPerformanceButton">Best of Today</button>
	<button class="categoryButton btn" id="recentPerformanceButton">Recent Attempts</button>
	<button class="categoryButton btn" id="statsButton">Quiz Statistics</button>
	<%out.println("<a id=\"quizButton\" class=\"categoryButton btn\" href='DisplayQuiz?id="+quiz.getID()+"' >Take Quiz</a>"); %>
	<!--------------------------Your Performances --------------------------->
	
	<div id = "yourPerformances">
		<h1>Your Past Performances</h1>
		<label id="sortLabel">Sort By:</label>
		<button class="categoryButton btn" id="byScore">Score</button>
		<button class="categoryButton btn" id="byDate">Date</button>
		<button class="categoryButton btn" id="byTime">Time</button><br><br>
		<div id = "scoreDiv">
			<% 
				List<Attempt> yourAttempts = new Vector<Attempt>();
				for (Attempt attempt: attempts) {
					if (attempt.user.equals(user)) {
						yourAttempts.add(attempt);
					}
				}
				Collections.sort(yourAttempts, new ScoreCompare());
				for (Attempt attempt: yourAttempts) {
					out.println("<label> " + attempt.user + "</label>" + "<p>" +  attempt.percentScore() + "</p>");
				}
			%>
		</div>
		<div id ="dateDiv">
			<%
				Collections.sort(yourAttempts, new DateCompare());
				for (Attempt attempt: yourAttempts) {
					out.println("<label> " + attempt.user + "</label>" + "<p>" +  attempt.getDate() + "</p>");
				}
			%>
		</div>
		<div id = "timeDiv">
			<%
				Collections.sort(yourAttempts, new TimeCompare());
			for (Attempt attempt: yourAttempts) {
				out.println("<label> " + attempt.user + "</label>" + "<p>" +  attempt.time + " seconds</p>");
			}
			%>
		</div>
	</div>
<!--------------------------Highest of all time --------------------------->
	
	<div id="allTimePerformances">
		<h1>Highest of All Time</h1>
		<% 
			Collections.sort(attempts, new ScoreCompare());
			int limit = 5;
			if (attempts.size() < 5) limit = attempts.size();
			for (int i = 0; i < limit; i++) {
				Attempt attempt = attempts.get(i);
				out.println("<label> " + attempt.user + "</label>" + "<p>" +  attempt.percentScore() + "</p>");
			}
		%>
	</div>
	<!--------------------------Highest of the Day --------------------------->
	<div id="dayPerformances">
		<h1>Highest of the Day</h1>
		<% 
			List<Attempt> todayAttempts = new Vector<Attempt>();
			Date today = new Date();
			
			
			final String OLD_FORMAT = "EEE MMM dd hh:mm:ss zzz yyyy";
			final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
			// August 12, 2010
			String oldDateString = today.toString();
			String newDateString;
	
			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d = sdf.parse(oldDateString);
			sdf.applyPattern(NEW_FORMAT);
			newDateString = sdf.format(d);
			
			
			
			String todayString = newDateString.substring(0,10);//cuts irrelevant info
			System.out.println(todayString);
			for (Attempt attempt: attempts) {
				String dateCompare = attempt.date.substring(0,10);//cuts irrelevant info
				if (todayString.equals(dateCompare)) todayAttempts.add(attempt); 
			}
			Collections.sort(todayAttempts, new ScoreCompare());
			for (Attempt attempt: todayAttempts) {
				out.println("<label> " + attempt.user + "</label>" + "<p>" +  attempt.percentScore() + "</p>");
			}
					
		%>
	</div>
	<!--------------------------Recent Performances --------------------------->
	<div id="recentPerformances">
		<h1>Recent Performances</h1>
		<% 
			Collections.sort(attempts, new DateCompare());
			for (Attempt attempt: attempts) {
				out.println("<label> " + attempt.user + "</label>" + "<p>" +  attempt.percentScore() + "</p>");
			}
		%>
	</div>
	
	
	
	<!--------------------------Performance Summary --------------------------->
	<div id="performanceSummary">
	<h1>Total Performance Summary</h1>
		<% 
			/*
			 * Compute the avergae
			 */
			double average;
			double denom = attempts.size();
			System.out.println(quiz.getTotalPoints());
			double num = 0;
			for (Attempt attempt: attempts) {
				num += attempt.getScore();
			}
			if (denom != 0) {
				average = num/denom;
				String avString = Double.toString(average*100);
				int i = 0;
				while (avString.charAt(i) != '.') {
					i++;
				}
				avString = avString.substring(0,i);
				out.println("<label>Average</label><p> " + avString + " %</p>");
			}
			else out.println("<label>Average</label><p>N/A</p>");
			/*
			 * Compute the median
			 */
			if (attempts.size() > 0) {
				int position = attempts.size()/2;
				Attempt median = attempts.get(position);
				out.println("<label>Median</label><p> " + median.percentScore() + "</p>");		
			}
			else out.println("<label>Median</label><p>N/A</p>");
		%>
		</div>

	
	
<%@ include file="helpers/end_boilerplate.jsp" %>