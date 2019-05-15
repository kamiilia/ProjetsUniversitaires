using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using td2.viewModel;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace td2.view
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ProfilPage : ContentPage
    {
        ProfilViewModel profilViewModel;

        public ProfilPage()
        {
            InitializeComponent();
            BindingContext = this.profilViewModel = new ProfilViewModel();
        }
        async void Editer_profil(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new EditerProfilPage(new EditerProfilViewModel(profilViewModel.User)));
        }
        async void Editer_password(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new EditerMotDePassPage());

        }

    }
}
