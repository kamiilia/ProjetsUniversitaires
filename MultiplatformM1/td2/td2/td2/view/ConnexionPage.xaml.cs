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
	public partial class ConnexionPage : ContentPage
	{
        ConnexionViewModel connexionViewModel;
		public ConnexionPage ()
		{   
			InitializeComponent ();
            BindingContext = this.connexionViewModel = new ConnexionViewModel();

        }
        async void Connexion(object sender, EventArgs e) {

            var value = await connexionViewModel.Connexion(sender, e);

            if (value)
            {
                Navigation.InsertPageBefore(new MainPage(), this);
                await Navigation.PopAsync();
            }
        }
        async void Inscription(object sender, EventArgs e) {
            await Navigation.PushAsync(new InscriptionPage());
        }

    }
}