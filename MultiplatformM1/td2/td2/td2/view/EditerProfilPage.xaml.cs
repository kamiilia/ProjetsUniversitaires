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
	public partial class EditerProfilPage : ContentPage
	{
        EditerProfilViewModel editerProfilViewModel;
		public EditerProfilPage (EditerProfilViewModel editerProfilViewModel)
		{
			InitializeComponent ();
            BindingContext = this.editerProfilViewModel = editerProfilViewModel;

        }
        async void updateProfil(object sender,EventArgs e)
        {
            if (editerProfilViewModel.Update())
            {
                await Navigation.PopAsync();
            }
        }
    }
}