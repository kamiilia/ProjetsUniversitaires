using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

using td2.view;
using Item = td2.Model;

namespace td2.view
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MainPage : MasterDetailPage
    {
        Dictionary<int, NavigationPage> MenuPages = new Dictionary<int, NavigationPage>();

        public MainPage()
        {
            InitializeComponent();

            MasterBehavior = MasterBehavior.Popover;
            MenuPages.Add((int)Item.Menu.PlaceItem, (NavigationPage)Detail);
            NavigationPage.SetHasNavigationBar(this, false);
        }

        public async Task NavigateMenu(int Id)
        {
            if (!MenuPages.ContainsKey(Id))
            {
                switch (Id)
                {
                    case (int)Item.Menu.PlaceItem:
                        MenuPages.Add(Id, new NavigationPage(new PlaceItemPage()));
                        break;
                    case (int)Item.Menu.Profil:
                        MenuPages.Add(Id, new NavigationPage(new ProfilPage()));
                        break;
                    case (int)Item.Menu.Logout:
                        App.Result = null;
                        Navigation.InsertPageBefore(new ConnexionPage(), this);
                        await Navigation.PopAsync();
                        return;
                }
            }

            var newPage = MenuPages[Id];

            if (newPage != null && Detail != newPage)
            {
                Detail = newPage;

                if (Device.RuntimePlatform == Device.Android)
                    await Task.Delay(100);

                IsPresented = false;
            }
        }
    }
}
