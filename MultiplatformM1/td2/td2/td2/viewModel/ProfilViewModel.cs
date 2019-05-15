using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using td2.view;
using Xamarin.Forms;

namespace td2.viewModel
{
    public class ProfilViewModel: BaseViewModel
    {
        public Command loadProfileCommand { get; set; }

        private UserItem user;
        public UserItem User
        {
            get { return user; }
            set { SetProperty(ref user, value); }

        }
        
        public ProfilViewModel()
        {
            User = new UserItem();
            loadProfileCommand = new Command(async () => await LoadProfile());
            loadProfileCommand.Execute(null);

            MessagingCenter.Subscribe<EditerProfilViewModel, UpdateProfileRequest>(this, "updateProfile", async (obj, user) =>
            {
                var response = await restService.SetProfile(user);
                User = response;
            });
        }

        async Task LoadProfile()
        {
            User = await restService.GetProfile();
        }
    }
}
