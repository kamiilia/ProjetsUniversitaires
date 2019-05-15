using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;

namespace td2.viewModel
{
    public class EditerMotDePassViewModel :BaseViewModel
    {   
        public UpdatePasswordRequest UpdatePassword { get; set; }
        public string Password { get; set; }
        public string PasswordConfirm { get; set; }

        public EditerMotDePassViewModel()
        {
            UpdatePassword = new UpdatePasswordRequest();
        }

        public async Task<bool> Update()
        {
            if (Password.Length != 0 && Password.Equals(PasswordConfirm))
            {
                UpdatePassword.NewPassword = PasswordConfirm;

                return await restService.SetPasswordProfiles(UpdatePassword);
            }

            return false;
        }
    }
}
