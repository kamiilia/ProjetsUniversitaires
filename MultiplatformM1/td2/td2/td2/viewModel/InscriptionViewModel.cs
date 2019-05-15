using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using Xamarin.Forms;

namespace td2.viewModel
{
    public class InscriptionViewModel : BaseViewModel
    {
        public RegisterRequest Login { get; set; }
        private string error;
        public string Error
        {
            get { return error; }
            set { SetProperty(ref error, value); }
        }

        public InscriptionViewModel()
        {
            Login = new RegisterRequest();
        }

        public async Task<bool> Inscription(object sender, EventArgs e)
        {
            if (Login.Email.Length != 0 && Login.FirstName.Length != 0 &&
                Login.LastName.Length != 0 && Login.Password.Length != 0)
            {

                var RegisterResult = await restService.Inscription(Login);

                if (RegisterResult.ExpiresIn == 0)
                {
                    Error = "Utilisateur inconnu";
                    return await Task.FromResult(false);
                }

                App.Result = RegisterResult;

                return await Task.FromResult(true);
            }

            Error = "Champ manquant";
            return await Task.FromResult(false);
        }
    }
}
