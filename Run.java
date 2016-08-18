import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Run extends HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
       	String path = request.getServletContext().getRealPath("/");
		PrintWriter jspout = response.getWriter();
		String filename = request.getParameter("className").trim();
		String command = "java -cp \""+path+"\\Files\\classes\""+" "+filename;
		System.out.println(command);
		Process out = Runtime.getRuntime().exec(command);
		try
		{
			out.waitFor();
			BufferedReader output = new BufferedReader(new InputStreamReader(out.getInputStream()));
			BufferedReader error = new BufferedReader(new InputStreamReader(out.getErrorStream()));
			String result="";
			while(true)
			{
				String temp = output.readLine();
				if(temp!=null){
					result+= temp;
					result+= "\n";
				}
				else{
					break;
				}
			}
			if(result.equals("")){
				while(true)
				{
					String temp = error.readLine();
					if(temp!=null){
						result+= temp;
					}	
					else{
						break;
					}
				}
			}
			jspout.println(result);
			output.close();
			error.close();
		}catch(InterruptedException e){ }
    }
}