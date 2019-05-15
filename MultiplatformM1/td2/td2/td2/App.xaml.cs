using System;
using TD.Api.Dtos;
using td2.view;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

[assembly: XamlCompilation(XamlCompilationOptions.Compile)]
namespace td2
{
    public partial class App : Application
    {
        public static LoginResult Result { get; set; }

        public App()
        {
            InitializeComponent();

            if (Result == null)
            {
                MainPage = new NavigationPage(new ConnexionPage());
            }
            else
            {
                MainPage = new NavigationPage(new MainPage());
            }
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
