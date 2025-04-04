package com.example.snakeyaml;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import java.util.Map;
import java.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import static spark.Spark.*;

public class App {


    public static void main(String[] args) {

        port(8080);

        get("/*", (req, res) -> {
            Student student = new Student();

            student.setId(21);
            student.setFirstName("Tim");
            student.setLastName("Doe");
            student.setAge(21);
            student.setDepartment("Cyberware");

            Course courseOne = new Course();
            courseOne.setName("Intelligence");
            courseOne.setCredits(5);

            Course courseTwo = new Course();
            courseTwo.setName("Crafting");
            courseTwo.setCredits(2);

            List<Course> courseList = new ArrayList<>();
            courseList.add(courseOne);
            courseList.add(courseTwo);

            student.setCourses(courseList);

            DumperOptions options = new DumperOptions();
            options.setIndent(2);
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);

            res.type("text/plain");
            return yaml.dump(student);
        });

        post("/*", (req, res) -> {

            String body = req.body();

            InputStream inputStream = new ByteArrayInputStream(body.getBytes());
            LoaderOptions loaderOptions = new LoaderOptions();
            loaderOptions.setAllowRecursiveKeys(false); // prevent recursive keys
            loaderOptions.setMaxAliasesForCollections(50); // limits the number of aliases expansion to prevent DoS
            try {
                Yaml yaml = new Yaml(new SafeConstructor(loaderOptions)); // use SafeConstructor to prevent arbitrary code execution
                Student data = yaml.loadAs(inputStream, Student.class);
                System.out.println(data);
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                res.status(400);
                res.type("text/plain");
                return "Error: " + e.getMessage();
            }

            res.type("text/plain");
            return "File Read, Object Created";
        });
    }
}
