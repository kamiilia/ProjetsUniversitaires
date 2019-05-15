using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using Xamarin.Forms;

namespace td2.viewModel
{
    public class EditerProfilViewModel:BaseViewModel
    {
        public UpdateProfileRequest UpdateProfile { get; set; }

        public EditerProfilViewModel(UserItem userItem)
        {
            UpdateProfile = new UpdateProfileRequest();

            UpdateProfile.FirstName = userItem.FirstName;
            UpdateProfile.LastName = userItem.LastName;
            UpdateProfile.ImageId = userItem.ImageId;
        }

        public bool Update()
        {
            if (UpdateProfile.FirstName.Length != 0 && UpdateProfile.LastName.Length != 0)
            {
                MessagingCenter.Send(this, "updateProfile", UpdateProfile);

                return true;
            }

            return false;
        }
    }
}
