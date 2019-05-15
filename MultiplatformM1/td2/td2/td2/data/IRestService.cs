using Common.Api.Dtos;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;

namespace td2.data
{
    public interface IRestService
    {
        Task<List<PlaceItemSummary>> RefreshDataAsync();
        Task<PlaceItem> FindPlaceItemById(int Id);

        Task<bool> Add_comment(int Id, CreateCommentRequest commentItem);
        Task<LoginResult> Connexion(LoginRequest login);
        Task<LoginResult> Inscription(RegisterRequest register);
        Task<UserItem> GetProfile();
        Task<UserItem> SetProfile(UpdateProfileRequest updateProfileRequest);
        Task<bool> SetPasswordProfiles(UpdatePasswordRequest updatePasswordRequest);
        Task<ImageItem> PostImage(byte[] image);
        Task<bool> PostPlaces(PlaceItem placeItem);

        //Task SavePlaceItem(PlaceItem item, bool isNewItem);



        //Task DeletePlaceItem(string id);

    }
}
