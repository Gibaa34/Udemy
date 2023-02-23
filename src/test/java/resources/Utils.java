package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification requestSpec;

    public RequestSpecification requestSpecification() throws IOException {

        if(requestSpec == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));

            requestSpec = new RequestSpecBuilder().setBaseUri(getGlobalValue("BaseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return requestSpec;
        }
        return requestSpec;
    }

    public static String getGlobalValue(String key) throws IOException {

        Properties properties = new Properties();
        FileInputStream variables = new FileInputStream("C:\\Users\\PlamenManolov\\IdeaProjects\\Cucumber\\src\\test\\java\\resources\\global.properties");
        properties.load(variables);
        return properties.getProperty(key);

    }

    public static String getJsonPath(Response response, String key) {

        String resp = response.asString();
        JsonPath responseJson = new JsonPath(resp);
        //System.out.println(resp);
        //System.out.println(responseJson.get(key).toString());
        return responseJson.get(key).toString();
    }

}
