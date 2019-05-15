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
	public partial class CommentsPage : ContentPage
	{
        public CommentsViewModel CommentsViewModel { get; set; }

		public CommentsPage (CommentsViewModel commentsViewModel)
		{
			InitializeComponent ();

            BindingContext = CommentsViewModel = commentsViewModel;
		}
        async void Add_Comment(object sender,EventArgs e)
        {
            await CommentsViewModel.Add_Comment(sender, e);

        }
    }
}