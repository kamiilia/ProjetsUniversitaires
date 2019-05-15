using Common.Api.Dtos;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;

namespace td2.data
{
    class RestService : IRestService
    {
        private readonly string url = "https://td-api.julienmialon.com/";

        HttpClient client;
        public List<PlaceItemSummary> Items { get; private set; }
        public RestService()
        {
            client = new HttpClient();
         }

        public async Task<List<PlaceItemSummary>> RefreshDataAsync()
        {
            List<PlaceItemSummary> list = new List<PlaceItemSummary>();
            var uri = new Uri(url + "places");

            try
            {
                client.DefaultRequestHeaders.Accept.Clear();
                var response = await client.GetAsync(uri);

                if (response.IsSuccessStatusCode)
                {
                    var content = await response.Content.ReadAsStringAsync();
                    list = JsonConvert.DeserializeObject<Response<List<PlaceItemSummary>>>(content).Data;
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(@"				ERROR {0}", ex.Message);
            }

            return await Task.FromResult(list);
        }

        public async Task<PlaceItem> FindPlaceItemById(int Id)
        {
            PlaceItem item = new PlaceItem();
            var uri = new Uri(url + "places/" + Id);

            try
            {
                client.DefaultRequestHeaders.Accept.Clear();
                var response = await client.GetAsync(uri);

                if (response.IsSuccessStatusCode)
                {
                    var content = await response.Content.ReadAsStringAsync();
                    item = JsonConvert.DeserializeObject<Response<PlaceItem>>(content).Data;
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(@"				ERROR {0}", ex.Message);
            }

            return await Task.FromResult(item);
        }
        public async Task<bool> Add_comment(int Id, CreateCommentRequest commentItem)
        {
            string commentJson = JsonConvert.SerializeObject(commentItem);
            string sContentType = "application/json";
            var sUrl = url + "places/" + Id + "/comments";
            
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(App.Result.TokenType, App.Result.AccessToken);
            var oTaskPostAsync = await client.PostAsync(sUrl, new StringContent(commentJson, Encoding.UTF8, sContentType));

            return await Task.FromResult(oTaskPostAsync.IsSuccessStatusCode);
        }

        public async Task<LoginResult> Connexion(LoginRequest login)
        {
            LoginResult loginResult = new LoginResult();

            string authJson = JsonConvert.SerializeObject(login);
            string sContentType = "application/json";
            var sUrl = url + "auth/login";
            client.DefaultRequestHeaders.Accept.Clear();
            var oTaskPostAsync = await client.PostAsync(sUrl, new StringContent(authJson, Encoding.UTF8, sContentType));

            if (oTaskPostAsync.IsSuccessStatusCode)
            {
                var content = await oTaskPostAsync.Content.ReadAsStringAsync();
                loginResult = JsonConvert.DeserializeObject<Response<LoginResult>>(content).Data;
            }

            return await Task.FromResult(loginResult);
        }

        public async Task<LoginResult> Inscription(RegisterRequest register)
        {
            LoginResult loginResult = new LoginResult();

            string registerJson = JsonConvert.SerializeObject(register);
            string sContentType = "application/json";
            var sUrl = url + "auth/register";
            client.DefaultRequestHeaders.Accept.Clear();
            var oTaskPostAsync = await client.PostAsync(sUrl, new StringContent(registerJson, Encoding.UTF8, sContentType));

            if (oTaskPostAsync.IsSuccessStatusCode)
            {
                var content = await oTaskPostAsync.Content.ReadAsStringAsync();
                loginResult = JsonConvert.DeserializeObject<Response<LoginResult>>(content).Data;
            }

            return await Task.FromResult(loginResult);
        }

        private async Task<bool> RefreshToken()
        {
            RefreshRequest refreshRequest = new RefreshRequest
            {
                RefreshToken = App.Result.RefreshToken
            };

            string refreshJson = JsonConvert.SerializeObject(refreshRequest);
            string sContentType = "application/json";
            var sUrl = url + "auth/refresh";
            client.DefaultRequestHeaders.Accept.Clear();
            var oTaskPostAsync = await client.PostAsync(sUrl, new StringContent(refreshJson, Encoding.UTF8, sContentType));

            if (oTaskPostAsync.IsSuccessStatusCode)
            {
                var content = await oTaskPostAsync.Content.ReadAsStringAsync();
                App.Result = JsonConvert.DeserializeObject<Response<LoginResult>>(content).Data;
            }

            return await Task.FromResult(oTaskPostAsync.IsSuccessStatusCode);
        }

        public async Task<UserItem> GetProfile()
        {
            UserItem userItem = new UserItem();
            var sUrl = url + "me";

            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(App.Result.TokenType, App.Result.AccessToken);
            var response = await client.GetAsync(sUrl);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                userItem = JsonConvert.DeserializeObject<Response<UserItem>>(content).Data;
            }

            return await Task.FromResult(userItem);
        }

        public async Task<UserItem> SetProfile(UpdateProfileRequest updateProfileRequest)
        {
            UserItem userItem = new UserItem();
            string profilJson = JsonConvert.SerializeObject(updateProfileRequest);
            string sContentType = "application/json";
            var sUrl = url + "me";

            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(App.Result.TokenType, App.Result.AccessToken);

            var http = new HttpRequestMessage(new HttpMethod("PATCH"), sUrl);

            var stringContent = new StringContent(profilJson, Encoding.UTF8, sContentType);
            http.Content = stringContent;

            var oTaskPostAsync = await client.SendAsync(http);

            if (oTaskPostAsync.IsSuccessStatusCode)
            {
                var content = await oTaskPostAsync.Content.ReadAsStringAsync();
                userItem = JsonConvert.DeserializeObject<Response<UserItem>>(content).Data;
            }

            return await Task.FromResult(userItem);
        }

        public async Task<bool> SetPasswordProfiles(UpdatePasswordRequest updatePasswordRequest)
        {
            string passwordJson = JsonConvert.SerializeObject(updatePasswordRequest);
            string sContentType = "application/json";
            var sUrl = url + "me/password";

            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(App.Result.TokenType, App.Result.AccessToken);

            var http = new HttpRequestMessage(new HttpMethod("PATCH"), sUrl);

            var stringContent = new StringContent(passwordJson, Encoding.UTF8, sContentType);
            http.Content = stringContent;

            var oTaskPostAsync = await client.SendAsync(http);

            return await Task.FromResult(oTaskPostAsync.IsSuccessStatusCode);
        }

        public async Task<ImageItem> PostImage(byte[] image)
        {
            var imageItem = new ImageItem();
            var sUrl = url + "images";

            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, sUrl);
            request.Headers.Authorization = new AuthenticationHeaderValue(App.Result.TokenType, App.Result.AccessToken);

            MultipartFormDataContent content = new MultipartFormDataContent();
            var imageContent = new ByteArrayContent(image);
            imageContent.Headers.ContentType = MediaTypeHeaderValue.Parse("image/jpeg");
            content.Add(imageContent, "file", "file.jpg");

            request.Content = content;
            var oTaskPostAsync = await client.SendAsync(request);

            if (oTaskPostAsync.IsSuccessStatusCode)
            {
                var response = await oTaskPostAsync.Content.ReadAsStringAsync();
                imageItem = JsonConvert.DeserializeObject<Response<ImageItem>>(response).Data;
            }

            return await Task.FromResult(imageItem);
        }

        public async Task<bool> PostPlaces(PlaceItem placeItem)
        {
            string placesJson = JsonConvert.SerializeObject(placeItem);
            string sContentType = "application/json";
            var sUrl = url + "places";

            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(App.Result.TokenType, App.Result.AccessToken);
            var oTaskPostAsync = await client.PostAsync(sUrl, new StringContent(placesJson, Encoding.UTF8, sContentType));

            return await Task.FromResult(oTaskPostAsync.IsSuccessStatusCode);
        }
    }
 }

