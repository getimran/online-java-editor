import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Compile extends HttpServlet
{

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		String path = request.getServletContext().getRealPath("/");
		PrintWriter out = response.getWriter();
		String filename = request.getParameter("className")+".java";
		FileOutputStream fout = new FileOutputStream(new File(path+"//Files//"+filename));
		byte b[] = request.getParameter("code").getBytes(); 
		fout.write(b);
		String command = "javac -d \""+path+"\\Files\\classes\""+" \""+path+"\\Files\\"+filename+"\"";
		System.out.println(command);
		Process error = Runtime.getRuntime().exec(command);
		BufferedReader err = new BufferedReader(new InputStreamReader(error.getErrorStream()));
		String result="";
		while(true)
		{
			String temp = err.readLine();
			if(temp!=null)
			{
				result+= temp;
				result+= "\n";
			}
			else
			break;
		}
		if(result.equals("")){
		out.println("Compiled Successfully");
		err.close();
		}
		out.println(result);
	}
}