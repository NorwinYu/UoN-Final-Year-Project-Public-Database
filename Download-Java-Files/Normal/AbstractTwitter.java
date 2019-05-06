package edu.woodson.lab;

import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.util.function.Consumer;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/13/2019
 */
abstract class AbstractTwitter implements Twitter {
    @Override
    public TimelinesResources timelines() {
        return null;
    }

    @Override
    public TweetsResources tweets() {
        return null;
    }

    @Override
    public SearchResource search() {
        return null;
    }

    @Override
    public DirectMessagesResources directMessages() {
        return null;
    }

    @Override
    public FriendsFollowersResources friendsFollowers() {
        return null;
    }

    @Override
    public UsersResources users() {
        return null;
    }

    @Override
    public SuggestedUsersResources suggestedUsers() {
        return null;
    }

    @Override
    public FavoritesResources favorites() {
        return null;
    }

    @Override
    public ListsResources list() {
        return null;
    }

    @Override
    public SavedSearchesResources savedSearches() {
        return null;
    }

    @Override
    public PlacesGeoResources placesGeo() {
        return null;
    }

    @Override
    public TrendsResources trends() {
        return null;
    }

    @Override
    public SpamReportingResource spamReporting() {
        return null;
    }

    @Override
    public HelpResources help() {
        return null;
    }

    @Override
    public String getScreenName() throws IllegalStateException {
        return null;
    }

    @Override
    public long getId() throws IllegalStateException {
        return 0;
    }

    @Override
    public void addRateLimitStatusListener(RateLimitStatusListener listener) {

    }

    @Override
    public void onRateLimitStatus(Consumer<RateLimitStatusEvent> action) {

    }

    @Override
    public void onRateLimitReached(Consumer<RateLimitStatusEvent> action) {

    }

    @Override
    public Authorization getAuthorization() {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getDirectMessages() {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getDirectMessages(Paging paging) {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getSentDirectMessages() {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getSentDirectMessages(Paging paging) {
        return null;
    }

    @Override
    public DirectMessageList getDirectMessages(int count) {
        return null;
    }

    @Override
    public DirectMessageList getDirectMessages(int count, String cursor) {
        return null;
    }

    @Override
    public DirectMessage showDirectMessage(long id) {
        return null;
    }

    @Override
    public DirectMessage destroyDirectMessage(long id) {
        return null;
    }

    @Override
    public DirectMessage sendDirectMessage(long userId, String text) {
        return null;
    }

    @Override
    public DirectMessage sendDirectMessage(long userId, String text, long mediaId) {
        return null;
    }

    @Override
    public DirectMessage sendDirectMessage(String screenName, String text) {
        return null;
    }

    @Override
    public InputStream getDMImageAsStream(String url) {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites() {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(long userId) {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(String screenName) {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(long userId, Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(String screenName, Paging paging) {
        return null;
    }

    @Override
    public Status createFavorite(long id) {
        return null;
    }

    @Override
    public Status destroyFavorite(long id) {
        return null;
    }

    @Override
    public IDs getNoRetweetsFriendships() {
        return null;
    }

    @Override
    public IDs getFriendsIDs(long cursor) {
        return null;
    }

    @Override
    public IDs getFriendsIDs(long userId, long cursor) {
        return null;
    }

    @Override
    public IDs getFriendsIDs(long userId, long cursor, int count) {
        return null;
    }

    @Override
    public IDs getFriendsIDs(String screenName, long cursor) {
        return null;
    }

    @Override
    public IDs getFriendsIDs(String screenName, long cursor, int count) {
        return null;
    }

    @Override
    public IDs getFollowersIDs(long cursor) {
        return null;
    }

    @Override
    public IDs getFollowersIDs(long userId, long cursor) {
        return null;
    }

    @Override
    public IDs getFollowersIDs(long userId, long cursor, int count) {
        return null;
    }

    @Override
    public IDs getFollowersIDs(String screenName, long cursor) {
        return null;
    }

    @Override
    public IDs getFollowersIDs(String screenName, long cursor, int count) {
        return null;
    }

    @Override
    public ResponseList<Friendship> lookupFriendships(long... ids) {
        return null;
    }

    @Override
    public ResponseList<Friendship> lookupFriendships(String... screenNames) {
        return null;
    }

    @Override
    public IDs getIncomingFriendships(long cursor) {
        return null;
    }

    @Override
    public IDs getOutgoingFriendships(long cursor) {
        return null;
    }

    @Override
    public User createFriendship(long userId) {
        return null;
    }

    @Override
    public User createFriendship(String screenName) {
        return null;
    }

    @Override
    public User createFriendship(long userId, boolean follow) {
        return null;
    }

    @Override
    public User createFriendship(String screenName, boolean follow) {
        return null;
    }

    @Override
    public User destroyFriendship(long userId) {
        return null;
    }

    @Override
    public User destroyFriendship(String screenName) {
        return null;
    }

    @Override
    public Relationship updateFriendship(long userId, boolean enableDeviceNotification, boolean retweets) {
        return null;
    }

    @Override
    public Relationship updateFriendship(String screenName, boolean enableDeviceNotification, boolean retweets) {
        return null;
    }

    @Override
    public Relationship showFriendship(long sourceId, long targetId) {
        return null;
    }

    @Override
    public Relationship showFriendship(String sourceScreenName, String targetScreenName) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsList(long userId, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsList(long userId, long cursor, int count) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsList(String screenName, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsList(String screenName, long cursor, int count) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsList(long userId, long cursor, int count, boolean skipStatus, boolean includeUserEntities) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsList(String screenName, long cursor, int count, boolean skipStatus, boolean includeUserEntities) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersList(long userId, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersList(String screenName, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersList(long userId, long cursor, int count) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersList(String screenName, long cursor, int count) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersList(long userId, long cursor, int count, boolean skipStatus, boolean includeUserEntities) {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersList(String screenName, long cursor, int count, boolean skipStatus, boolean includeUserEntities) {
        return null;
    }

    @Override
    public TwitterAPIConfiguration getAPIConfiguration() {
        return null;
    }

    @Override
    public ResponseList<Language> getLanguages() {
        return null;
    }

    @Override
    public String getPrivacyPolicy() {
        return null;
    }

    @Override
    public String getTermsOfService() {
        return null;
    }

    @Override
    public Map<String, RateLimitStatus> getRateLimitStatus() {
        return null;
    }

    @Override
    public Map<String, RateLimitStatus> getRateLimitStatus(String... resources) {
        return null;
    }

    @Override
    public ResponseList<UserList> getUserLists(String listOwnerScreenName) {
        return null;
    }

    @Override
    public ResponseList<UserList> getUserLists(String listOwnerScreenName, boolean reverse) {
        return null;
    }

    @Override
    public ResponseList<UserList> getUserLists(long listOwnerUserId) {
        return null;
    }

    @Override
    public ResponseList<UserList> getUserLists(long listOwnerUserId, boolean reverse) {
        return null;
    }

    @Override
    public ResponseList<Status> getUserListStatuses(long listId, Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getUserListStatuses(long ownerId, String slug, Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getUserListStatuses(String ownerScreenName, String slug, Paging paging) {
        return null;
    }

    @Override
    public UserList destroyUserListMember(long listId, long userId) {
        return null;
    }

    @Override
    public UserList destroyUserListMember(long listId, String screenName) {
        return null;
    }

    @Override
    public UserList destroyUserListMembers(long listId, String[] screenNames) {
        return null;
    }

    @Override
    public UserList destroyUserListMembers(long listId, long[] userIds) {
        return null;
    }

    @Override
    public UserList destroyUserListMembers(String ownerScreenName, String slug, String[] screenNames) {
        return null;
    }

    @Override
    public UserList destroyUserListMember(long ownerId, String slug, long userId) {
        return null;
    }

    @Override
    public UserList destroyUserListMember(String ownerScreenName, String slug, long userId) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(long listMemberId, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(long listMemberId, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName, long cursor, boolean filterToOwnedLists) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName, int count, long cursor, boolean filterToOwnedLists) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(long listMemberId, long cursor, boolean filterToOwnedLists) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(long listMemberId, int count, long cursor, boolean filterToOwnedLists) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(long listId, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(long listId, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(long listId, int count, long cursor, boolean skipStatus) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(long ownerId, String slug, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(long ownerId, String slug, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(long ownerId, String slug, int count, long cursor, boolean skipStatus) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(String ownerScreenName, String slug, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(String ownerScreenName, String slug, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(String ownerScreenName, String slug, int count, long cursor, boolean skipStatus) {
        return null;
    }

    @Override
    public UserList createUserListSubscription(long listId) {
        return null;
    }

    @Override
    public UserList createUserListSubscription(long ownerId, String slug) {
        return null;
    }

    @Override
    public UserList createUserListSubscription(String ownerScreenName, String slug) {
        return null;
    }

    @Override
    public User showUserListSubscription(long listId, long userId) {
        return null;
    }

    @Override
    public User showUserListSubscription(long ownerId, String slug, long userId) {
        return null;
    }

    @Override
    public User showUserListSubscription(String ownerScreenName, String slug, long userId) {
        return null;
    }

    @Override
    public UserList destroyUserListSubscription(long listId) {
        return null;
    }

    @Override
    public UserList destroyUserListSubscription(long ownerId, String slug) {
        return null;
    }

    @Override
    public UserList destroyUserListSubscription(String ownerScreenName, String slug) {
        return null;
    }

    @Override
    public UserList createUserListMembers(long listId, long... userIds) {
        return null;
    }

    @Override
    public UserList createUserListMembers(long ownerId, String slug, long... userIds) {
        return null;
    }

    @Override
    public UserList createUserListMembers(String ownerScreenName, String slug, long... userIds) {
        return null;
    }

    @Override
    public UserList createUserListMembers(long listId, String... screenNames) {
        return null;
    }

    @Override
    public UserList createUserListMembers(long ownerId, String slug, String... screenNames) {
        return null;
    }

    @Override
    public UserList createUserListMembers(String ownerScreenName, String slug, String... screenNames) {
        return null;
    }

    @Override
    public User showUserListMembership(long listId, long userId) {
        return null;
    }

    @Override
    public User showUserListMembership(long ownerId, String slug, long userId) {
        return null;
    }

    @Override
    public User showUserListMembership(String ownerScreenName, String slug, long userId) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(long listId, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(long listId, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(long listId, int count, long cursor, boolean skipStatus) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(long ownerId, String slug, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(long ownerId, String slug, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(long ownerId, String slug, int count, long cursor, boolean skipStatus) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(String ownerScreenName, String slug, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(String ownerScreenName, String slug, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(String ownerScreenName, String slug, int count, long cursor, boolean skipStatus) {
        return null;
    }

    @Override
    public UserList createUserListMember(long listId, long userId) {
        return null;
    }

    @Override
    public UserList createUserListMember(long ownerId, String slug, long userId) {
        return null;
    }

    @Override
    public UserList createUserListMember(String ownerScreenName, String slug, long userId) {
        return null;
    }

    @Override
    public UserList destroyUserList(long listId) {
        return null;
    }

    @Override
    public UserList destroyUserList(long ownerId, String slug) {
        return null;
    }

    @Override
    public UserList destroyUserList(String ownerScreenName, String slug) {
        return null;
    }

    @Override
    public UserList updateUserList(long listId, String newListName, boolean isPublicList, String newDescription) {
        return null;
    }

    @Override
    public UserList updateUserList(long ownerId, String slug, String newListName, boolean isPublicList, String newDescription) {
        return null;
    }

    @Override
    public UserList updateUserList(String ownerScreenName, String slug, String newListName, boolean isPublicList, String newDescription) {
        return null;
    }

    @Override
    public UserList createUserList(String listName, boolean isPublicList, String description) {
        return null;
    }

    @Override
    public UserList showUserList(long listId) {
        return null;
    }

    @Override
    public UserList showUserList(long ownerId, String slug) {
        return null;
    }

    @Override
    public UserList showUserList(String ownerScreenName, String slug) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListSubscriptions(String listSubscriberScreenName, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListSubscriptions(String listSubscriberScreenName, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListSubscriptions(long listSubscriberId, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListSubscriptions(long listSubscriberId, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListsOwnerships(String listOwnerScreenName, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListsOwnerships(String listOwnerScreenName, int count, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListsOwnerships(long listOwnerId, long cursor) {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListsOwnerships(long listOwnerId, int count, long cursor) {
        return null;
    }

    @Override
    public Place getGeoDetails(String placeId) {
        return null;
    }

    @Override
    public ResponseList<Place> reverseGeoCode(GeoQuery query) {
        return null;
    }

    @Override
    public ResponseList<Place> searchPlaces(GeoQuery query) {
        return null;
    }

    @Override
    public ResponseList<Place> getSimilarPlaces(GeoLocation location, String name, String containedWithin, String streetAddress) {
        return null;
    }

    @Override
    public ResponseList<SavedSearch> getSavedSearches() {
        return null;
    }

    @Override
    public SavedSearch showSavedSearch(long id) {
        return null;
    }

    @Override
    public SavedSearch createSavedSearch(String query) {
        return null;
    }

    @Override
    public SavedSearch destroySavedSearch(long id) {
        return null;
    }

    @Override
    public QueryResult search(Query query) {
        return null;
    }

    @Override
    public User reportSpam(long userId) {
        return null;
    }

    @Override
    public User reportSpam(String screenName) {
        return null;
    }

    @Override
    public ResponseList<User> getUserSuggestions(String categorySlug) {
        return null;
    }

    @Override
    public ResponseList<Category> getSuggestedUserCategories() {
        return null;
    }

    @Override
    public ResponseList<User> getMemberSuggestions(String categorySlug) {
        return null;
    }

    @Override
    public ResponseList<Status> getMentionsTimeline() {
        return null;
    }

    @Override
    public ResponseList<Status> getMentionsTimeline(Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(String screenName, Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(long userId, Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(String screenName) {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(long userId) {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline() {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getHomeTimeline() {
        return null;
    }

    @Override
    public ResponseList<Status> getHomeTimeline(Paging paging) {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetsOfMe() {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetsOfMe(Paging paging) {
        return null;
    }

    @Override
    public Trends getPlaceTrends(int woeid) {
        return null;
    }

    @Override
    public ResponseList<Location> getAvailableTrends() {
        return null;
    }

    @Override
    public ResponseList<Location> getClosestTrends(GeoLocation location) {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweets(long statusId) {
        return null;
    }

    @Override
    public IDs getRetweeterIds(long statusId, long cursor) {
        return null;
    }

    @Override
    public IDs getRetweeterIds(long statusId, int count, long cursor) {
        return null;
    }

    @Override
    public Status showStatus(long id) {
        return null;
    }

    @Override
    public Status destroyStatus(long statusId) {
        return null;
    }

    @Override
    public Status updateStatus(String status) {
        return null;
    }

    @Override
    public Status updateStatus(StatusUpdate latestStatus) {
        return null;
    }

    @Override
    public Status retweetStatus(long statusId) {
        return null;
    }

    @Override
    public Status unRetweetStatus(long statusId) {
        return null;
    }

    @Override
    public OEmbed getOEmbed(OEmbedRequest req) {
        return null;
    }

    @Override
    public ResponseList<Status> lookup(long... ids) {
        return null;
    }

    @Override
    public UploadedMedia uploadMedia(File mediaFile) {
        return null;
    }

    @Override
    public UploadedMedia uploadMedia(String fileName, InputStream media) {
        return null;
    }

    @Override
    public UploadedMedia uploadMediaChunked(String fileName, InputStream media) {
        return null;
    }

    @Override
    public AccountSettings getAccountSettings() {
        return null;
    }

    @Override
    public User verifyCredentials() {
        return null;
    }

    @Override
    public AccountSettings updateAccountSettings(Integer trendLocationWoeid, Boolean sleepTimeEnabled, String startSleepTime, String endSleepTime, String timeZone, String lang) {
        return null;
    }

    @Override
    public AccountSettings updateAllowDmsFrom(String allowDmsFrom) {
        return null;
    }

    @Override
    public User updateProfile(String name, String url, String location, String description) {
        return null;
    }

    @Override
    public User updateProfileBackgroundImage(File image, boolean tile) {
        return null;
    }

    @Override
    public User updateProfileBackgroundImage(InputStream image, boolean tile) {
        return null;
    }

    @Override
    public User updateProfileColors(String profileBackgroundColor, String profileTextColor, String profileLinkColor, String profileSidebarFillColor, String profileSidebarBorderColor) {
        return null;
    }

    @Override
    public User updateProfileImage(File image) {
        return null;
    }

    @Override
    public User updateProfileImage(InputStream image) {
        return null;
    }

    @Override
    public PagableResponseList<User> getBlocksList() {
        return null;
    }

    @Override
    public PagableResponseList<User> getBlocksList(long cursor) {
        return null;
    }

    @Override
    public IDs getBlocksIDs() {
        return null;
    }

    @Override
    public IDs getBlocksIDs(long cursor) {
        return null;
    }

    @Override
    public User createBlock(long userId) {
        return null;
    }

    @Override
    public User createBlock(String screenName) {
        return null;
    }

    @Override
    public User destroyBlock(long userId) {
        return null;
    }

    @Override
    public User destroyBlock(String screen_name) {
        return null;
    }

    @Override
    public PagableResponseList<User> getMutesList(long cursor) {
        return null;
    }

    @Override
    public IDs getMutesIDs(long cursor) {
        return null;
    }

    @Override
    public User createMute(long userId) {
        return null;
    }

    @Override
    public User createMute(String screenName) {
        return null;
    }

    @Override
    public User destroyMute(long userId) {
        return null;
    }

    @Override
    public User destroyMute(String screenName) {
        return null;
    }

    @Override
    public ResponseList<User> lookupUsers(long... ids) {
        return null;
    }

    @Override
    public ResponseList<User> lookupUsers(String... screenNames) {
        return null;
    }

    @Override
    public User showUser(long userId) {
        return null;
    }

    @Override
    public User showUser(String screenName) {
        return null;
    }

    @Override
    public ResponseList<User> searchUsers(String query, int page) {
        return null;
    }

    @Override
    public ResponseList<User> getContributees(long userId) {
        return null;
    }

    @Override
    public ResponseList<User> getContributees(String screenName) {
        return null;
    }

    @Override
    public ResponseList<User> getContributors(long userId) {
        return null;
    }

    @Override
    public ResponseList<User> getContributors(String screenName) {
        return null;
    }

    @Override
    public void removeProfileBanner() {

    }

    @Override
    public void updateProfileBanner(File image) {

    }

    @Override
    public void updateProfileBanner(InputStream image) {

    }

    @Override
    public OAuth2Token getOAuth2Token() {
        return null;
    }

    @Override
    public void setOAuth2Token(OAuth2Token oauth2Token) {

    }

    @Override
    public void invalidateOAuth2Token() {

    }

    @Override
    public void setOAuthConsumer(String consumerKey, String consumerSecret) {

    }

    @Override
    public RequestToken getOAuthRequestToken() {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken(String callbackURL) {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType) {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType, String xAuthMode) {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken() {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(String oauthVerifier) {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(RequestToken requestToken) {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(String screenName, String password) {
        return null;
    }

    @Override
    public void setOAuthAccessToken(AccessToken accessToken) {

    }
}
