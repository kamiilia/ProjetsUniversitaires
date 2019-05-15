using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using MenuItem = td2.Model.MenuItem;
using Item = td2.Model;

namespace td2.view
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MenuPage : ContentPage
    {
        MainPage RootPage { get => (Application.Current.MainPage as NavigationPage).RootPage as MainPage; }
        List<MenuItem> List;

        public MenuPage()
        {
            InitializeComponent();

            List = new List<MenuItem>
            {
                new MenuItem {Id = Item.Menu.PlaceItem, Title = "PlaceItem"},
                new MenuItem {Id = Item.Menu.Profil, Title = "Mon Profil"},
                new MenuItem {Id = Item.Menu.Logout, Title = "Logout"}
            };

            listView.ItemsSource = List;

            listView.SelectedItem = List[0];
            listView.ItemSelected += async (sender, e) =>
            {
                if (e.SelectedItem == null)
                    return;

                var id = (int)((MenuItem)e.SelectedItem).Id;
                await RootPage.NavigateMenu(id);
            };
        }
    }

}