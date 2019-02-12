#Flowable JsonForm Renderer
####Json Form to HTML

This application is a JSON-form renderer for Flowable. It converts the result of the Flowable's API to an HTML form.

You can get the id from an HTTP request to localhost:8080/flowable-task/form-api/form-repository/form-definitions, there you must find the form by it's name, and then get the id. Flowable saves differents versions of the same form, so pay special atention to that.

Once you get the id, and with the application running, you can get the form through the following url:

localhost:8090/form-renderer/form/{id}

In case that flowable runs on a non-local server, you can modify the url from application.properties, which by default is http://admin:test@localhost:8080

