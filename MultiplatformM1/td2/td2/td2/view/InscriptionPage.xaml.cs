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
	public partial class InscriptionPage : ContentPage
	{
        InscriptionViewModel inscriptionViewModel;

		public InscriptionPage ()
		{
			InitializeComponent ();

            BindingContext = inscriptionViewModel = new InscriptionViewModel();
		}

        async void Inscription(object sender, EventArgs e)
        {
            if (await inscriptionViewModel.Inscription(sender, e))
            {
                Navigation.InsertPageBefore(new MainPage(), Navigation.NavigationStack.First());
                await Navigation.PopToRootAsync();
            }

        }
	}
}