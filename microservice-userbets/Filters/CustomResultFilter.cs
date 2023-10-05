namespace microservice_userbets.Filters
{
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Filters;

    public class CustomResultFilter : ResultFilterAttribute
    {
        public override void OnResultExecuting(ResultExecutingContext context)
        {
            if (context.Result is ObjectResult objectResult)
            {
                var statusCode = objectResult.StatusCode;
                var isSuccessStatusCode = statusCode >= 200 && statusCode < 300;

                if (isSuccessStatusCode)
                {
                    // Successful response
                    var data = objectResult.Value;
                    var dataType = data.GetType();

                    var dataProperty = dataType.GetProperty("Data");
                    var messageProperty = dataType.GetProperty("Message");

                    var responseData = dataProperty?.GetValue(data);
                    var responseMessage = messageProperty?.GetValue(data);

                    var response = new
                    {
                        status = "success",
                        data = responseData,
                        message = responseMessage
                    };

                    context.Result = new ObjectResult(response)
                    {
                        StatusCode = statusCode
                    };
                }
                else
                {
                    // Error response
                    var error = objectResult.Value;
                    var errorType = error.GetType();
                    var messageProperty = errorType.GetProperty("Message");
                    var errorMessage = messageProperty?.GetValue(error);

                    var response = new
                    {
                        status = "error",
                        data = error,
                        message = errorMessage
                    };

                    context.Result = new ObjectResult(response)
                    {
                        StatusCode = statusCode
                    };
                }
            }

            base.OnResultExecuting(context);
        }

        private string GetErrorMessage(ResultExecutingContext context)
        {
            // You can customize this method based on your requirements
            // Here, you might inspect the context to determine the custom error message
            return "Request failed";
        }
    }
}
