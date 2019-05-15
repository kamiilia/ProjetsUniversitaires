using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using Xamarin.Forms;

namespace td2.viewModel
{
    public class ConnexionViewModel : BaseViewModel
    {
        public LoginRequest Login { get; set; }
        private string error;
        public string Error
        {
            get { return error; }
            set { SetProperty(ref error, value); }
        }

        public ConnexionViewModel()
        {
            Login = new LoginRequest();

        }
        public async Task<bool> Connexion(object sender, EventArgs e)
        {
            var loginResult = await restService.Connexion(Login);

            if (loginResult.ExpiresIn == 0)
            {
                Error = "Utilisateur inconnu";
                return await Task.FromResult(false);
            }

            App.Result = loginResult;

            return await Task.FromResult(true);
        }
    }
}
